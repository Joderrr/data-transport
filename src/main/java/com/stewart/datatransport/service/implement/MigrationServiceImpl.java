package com.stewart.datatransport.service.implement;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stewart.datatransport.component.DatabaseOperation;
import com.stewart.datatransport.mapper.DataSetMapper;
import com.stewart.datatransport.mapper.DatabaseConfigMapper;
import com.stewart.datatransport.pojo.dto.MigrationData;
import com.stewart.datatransport.pojo.persistent.DataObject;
import com.stewart.datatransport.pojo.persistent.DataSet;
import com.stewart.datatransport.pojo.persistent.DatabaseConfig;
import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                        .eq(DatabaseConfig::getId, dataRelations.getRoot().getDatasourceId()));
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
        allData.put(dataRelations.getRoot().getTableName(), rootQuery);
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
                            .eq(DatabaseConfig::getId, target.getSelf().getDatasourceId()));
            Map<String, List<String>> relations= target.getRelationShip();
            for (Map.Entry<String, List<String>> conditions : relations.entrySet()) {
                String value = current.get(conditions.getKey());
                List<String> keys = conditions.getValue();
                script = resolveSql(script, value, keys);
            }
            List<Map<String, String>> results = databaseOperation.executeQueryScript(
                    DataSourceConfig.readFromPersistent(targetDbConfig), script);
            String targetName = target.getSelf().getTableName();
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
            String keyName = "#{" + key + "}";
            String keyValue = "'" + value + "'";
            sql = sql.replace(keyName, keyValue);
        }
        return sql;
    }


    /**
     * @see MigrationService#importMigrationPackage(DataSourceConfig, MultipartFile)
     */
    @Override
    public GeneralResponse importMigrationPackage(DataSourceConfig dataSourceConfig, MultipartFile file) {
        try {
            String oriStr = new String(file.getBytes());
            MigrationData migrationData = JSONObject.parseObject(oriStr, MigrationData.class);
            if(migrationData == null){
                return generateResponseObject(false);
            }
            DataSetConfig dataSetConfig = migrationData.getDataSetConfig();
            Map<String, List<Map<String, String>>> originData = migrationData.getMigrationData();
            DataRelations dataRelations = dataSetConfig.getDataRelations();
            DataObject root = dataRelations.getRoot();
            List<Relation> children = dataRelations.getChildren();
            List<DataObject> dataObjects = new ArrayList<>();
            dataObjects.add(root);
            getDataObjects(children, dataObjects);
            Map<String, List<String>> sqlMap = resolveSQLMap(originData);
            for (List<String> value : sqlMap.values()) {
                value.forEach(v -> databaseOperation.executeInsertScript(
                        dataSourceConfig, v));
            }
            return generateResponseObject(true);
        } catch (Exception e) {
            log.error("An exception occurs while parsing file to MigrationData, exception : {}", e.getMessage(), e);
            return generateResponseObject(false);
        }
    }

    /**
     * get data objects from relations by using recursion method
     *
     * @param relations     object relationship model
     * @param dataObjects   data object collection
     */
    private void getDataObjects(List<Relation> relations, List<DataObject> dataObjects){
        if(relations == null || relations.isEmpty()){
            return;
        }
        for (Relation relation : relations) {
            dataObjects.add(relation.getSelf());
            if(relation.getChildren() != null && !relation.getChildren().isEmpty()){
                getDataObjects(relation.getChildren(), dataObjects);
            }
        }
    }

    /**
     * resolve sql string from origin data structure and column values
     *
     * @param data      origin data
     * @return          resolved sql
     */
    private Map<String, List<String>> resolveSQLMap(Map<String, List<Map<String, String>>> data){
        Map<String, List<String>> sqlMap = new HashMap<>();
        data.entrySet().stream().forEach(entry -> {
            String objectName = entry.getKey();
            List<Map<String,String>> values = entry.getValue();
            List<String> sqls = values.stream().map(v -> sqlFactory(objectName, v)).collect(Collectors.toList());
            sqlMap.put(objectName, sqls);
        });
        return sqlMap;
    }

    /**
     * generate sql from param and value map
     *
     * @param objectName    target object name , like table_name
     * @param value         target object key and values
     * @return              resolved sql
     */
    private String sqlFactory(String objectName, Map<String,String> value){
        String preStr = "insert into " + objectName ;
        String middile = " values";
        StringBuilder front = new StringBuilder("(");
        StringBuilder end = new StringBuilder("(");
        ArrayList<Map.Entry<String, String>> entries = new ArrayList<>(value.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            if(i > 0){
                front.append(",");
                end.append(",");
            }
            front.append(entries.get(i).getKey());
            end.append("'").append(entries.get(i).getValue()).append("'");
        }
        front.append(") ");
        end.append(");");
        return preStr + front + middile + end;
    }

}
