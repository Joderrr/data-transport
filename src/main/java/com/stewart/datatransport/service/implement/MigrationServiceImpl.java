package com.stewart.datatransport.service.implement;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stewart.datatransport.component.DatabaseOperation;
import com.stewart.datatransport.mapper.DataSetMapper;
import com.stewart.datatransport.mapper.DatabaseConfigMapper;
import com.stewart.datatransport.pojo.dto.MigrationData;
import com.stewart.datatransport.pojo.persistent.DataSet;
import com.stewart.datatransport.pojo.persistent.DatabaseConfig;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
import com.stewart.datatransport.pojo.vo.dataset.DataRelations;
import com.stewart.datatransport.pojo.vo.dataset.DataSetConfig;
import com.stewart.datatransport.pojo.vo.dataset.Relation;
import com.stewart.datatransport.service.BaseService;
import com.stewart.datatransport.service.MigrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Migration Service Implementation
 *
 * @author stewart
 * @date 2023/6/11
 */
@Slf4j
@Service
public class MigrationServiceImpl extends BaseService implements MigrationService {

    @Resource
    DataSetMapper dataSetMapper;

    @Resource
    DatabaseConfigMapper databaseConfigMapper;

    @Resource
    DatabaseOperation databaseOperation;

    /**
     * @see MigrationService#generateMigratePackage(String, Map, HttpServletResponse)
     */
    @Override
    public void generateMigratePackage(String dataSetUniqueId, Map<String, String> condition, HttpServletResponse response) {
        //first, we need to get a root object with the given condition
        DataSet dataSet = dataSetMapper.selectOne(new LambdaQueryWrapper<DataSet>().eq(DataSet::getDataSetUniqueId, dataSetUniqueId));
        DataSetConfig dataSetConfig = DataSetConfig.readFromPersistent(dataSet);
        DataRelations dataRelations = dataSetConfig.getDataRelations();
        DatabaseConfig databaseConfig = databaseConfigMapper.selectOne(
                new LambdaQueryWrapper<DatabaseConfig>()
                        .eq(DatabaseConfig::getDatabaseUniqueId, dataRelations.getRoot().getDatasourceId()));
        List<Map<String, String>> rootQuery = databaseOperation.executeQueryScript(
                DataSourceConfig.readFromPersistent(databaseConfig), dataRelations.getRoot().getQueryScript(), condition);
        if(rootQuery.size() != 1){
            log.error("root has no data or more than one data");
            return;
        }
        //this is the root.
        Map<String, String> root = rootQuery.get(0);
        //get all children
        List<Relation> children = dataRelations.getChildren();
        //make a collection to collect objects
        Map<String,List<Map<String, String>>> allData = new HashMap<>();
        allData.put(dataRelations.getRoot().getName(), rootQuery);
        //get all data objects by root, use recursion
        getDatas(children, root, allData);

        //prepare the migration object, then write back
        MigrationData migrationData = new MigrationData(dataSetConfig, allData);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONBytes(migrationData));
        } catch (IOException e){
            log.error("an exception occurs while response write file back to customer, exception : {}", e.getMessage(), e);
        } finally {
            if(outputStream != null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error("error occurs while closing the out put stream, exception : {}", e.getMessage(), e);
                }
            }
        }

    }

    /**
     * recursion get data
     *
     * @param targets   children
     * @param current   current object
     * @param allDatas  a collection that contains all data
     */
    private void getDatas(List<Relation> targets, Map<String, String> current, Map<String, List<Map<String, String>>> allDatas){
        if(targets == null || targets.isEmpty()){
            return;
        }
        for (Relation target : targets) {
            String script = target.getSelf().getQueryScript();
            DatabaseConfig targetDbConfig = databaseConfigMapper.selectOne(
                    new LambdaQueryWrapper<DatabaseConfig>()
                            .eq(DatabaseConfig::getDatabaseUniqueId, target.getSelf().getDatasourceId()));
            Map<String, List<String>> relations= target.getRelationShip();
            for (Map.Entry<String, List<String>> conditions : relations.entrySet()) {
                String value = current.get(conditions.getKey());
                List<String> keys = conditions.getValue();
                script = resolveSql(script, value, keys);
            }
            List<Map<String, String>> results = databaseOperation.executeQueryScript(
                    DataSourceConfig.readFromPersistent(targetDbConfig), script);
            String targetName = target.getSelf().getName();
            if(allDatas.containsKey(targetName)){
                List<Map<String,String>> oldData = allDatas.get(targetName);
                oldData.addAll(results);
                allDatas.put(targetName, oldData);
            }else {
                allDatas.put(targetName, results);
            }
            for (Map<String, String> curr : results) {
                getDatas(target.getChildren(), curr, allDatas);
            }
        }
    }

    /**
     * resolve sql with given condition
     *
     * @param sql       configured sql with {} to cover it's key
     * @param value     the solo value
     * @param keys      the replaceable keys
     * @return          resolved sql
     */
    private String resolveSql(String sql, String value, List<String> keys){
        for (String key : keys) {
            String keyName = "{" + key + "}";
            sql = sql.replace(keyName, value);
        }
        return sql;
    }


    @Override
    public boolean importMigrationPackage(DataSourceConfig dataSourceConfig, MultipartFile file) {
        return false;
    }
}
