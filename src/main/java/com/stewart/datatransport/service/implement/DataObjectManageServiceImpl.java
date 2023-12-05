package com.stewart.datatransport.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stewart.datatransport.component.DatabaseOperation;
import com.stewart.datatransport.enums.ErrorCode;
import com.stewart.datatransport.enums.object.DataType;
import com.stewart.datatransport.exception.CustomException;
import com.stewart.datatransport.mapper.DataObjectMapper;
import com.stewart.datatransport.mapper.DataSetMapper;
import com.stewart.datatransport.mapper.DatabaseConfigMapper;
import com.stewart.datatransport.pojo.persistent.DataObject;
import com.stewart.datatransport.pojo.persistent.DataSet;
import com.stewart.datatransport.pojo.persistent.DatabaseConfig;
import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfig;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfigPageQueryParam;
import com.stewart.datatransport.service.BaseService;
import com.stewart.datatransport.service.DataObjectManageService;
import com.stewart.datatransport.service.DatabaseManageService;
import com.stewart.datatransport.util.JacksonUtil;
import com.stewart.datatransport.util.SqlAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * data object management service's implementation
 *
 * @author stewart
 * @date 2023/2/17
 */
@Service
@Slf4j
public class DataObjectManageServiceImpl extends BaseService implements DataObjectManageService {

    @Resource
    DatabaseOperation databaseOperation;

    @Resource
    DatabaseConfigMapper databaseConfigMapper;

    @Resource
    DatabaseManageService databaseManageService;

    @Resource
    DataObjectMapper dataObjectMapper;

    @Resource
    DataSetMapper dataSetMapper;

    /**
     * @see DataObjectManageService#executeQueryScript(DataObjectConfig)
     */
    @Override
    public GeneralResponse executeQueryScript(DataObjectConfig configuration) {
        DataSourceConfig dataSourceConfig = DataSourceConfig.readFromPersistent(databaseConfigMapper.selectOne(
                new LambdaQueryWrapper<DatabaseConfig>()
                        .eq(DatabaseConfig::getDatabaseUniqueId, configuration.getDatabaseId())
        ));
        List<Map<String, String>> result =  databaseOperation.executeQueryScript(dataSourceConfig, configuration.getQueryScript());
        return generateSuccessfulResponseObject(result);
    }

    /**
     * @see DataObjectManageService#saveDataObject(DataObjectConfig)
     */
    @Override
    public GeneralResponse saveDataObject(DataObjectConfig configuration) {
        configuration.setObjectUniqueId(generateUuid());
        configuration.setTableName(SqlAnalyzer.extractSingleTableName(configuration.getQueryScript()));
        configuration.setQueryCondition(SqlAnalyzer.extractFieldsFromWhereClause(configuration.getQueryScript()));
        int insert = dataObjectMapper.insert(configuration.toPersistent());
        return generateResponseObject(insert > 0, configuration, "data object insert failure");
    }

    /**
     * @see DataObjectManageService#updateDataObject(DataObjectConfig)
     */
    @Override
    public GeneralResponse updateDataObject(DataObjectConfig configuration) throws CustomException {
        checkDataObjectIsInUse(configuration.getObjectUniqueId());
        int update = dataObjectMapper.update(configuration.toPersistent(), new LambdaQueryWrapper<DataObject>().eq(DataObject::getDataObjectUniqueId, configuration.getObjectUniqueId()));
        return generateResponseObject(update > 0, configuration, "data object update failure");
    }

    /**
     * @see DataObjectManageService#deleteDataObject(DataObjectConfig) (DataObjectConfig)
     */
    @Override
    public GeneralResponse deleteDataObject(DataObjectConfig configuration) {
        int delete = dataObjectMapper.delete(new LambdaQueryWrapper<DataObject>()
                .eq(DataObject::getDataObjectUniqueId, configuration.getObjectUniqueId()));
        return generateResponseObject(delete > 0, "data object delete success", "data object delete failure");
    }

    /**
     * @see DataObjectManageService#queryDataObject(DataObjectConfigPageQueryParam)
     */
    @Override
    public GeneralResponse queryDataObject(DataObjectConfigPageQueryParam queryParam) {
        LambdaQueryWrapper<DataObject> wrapper = new LambdaQueryWrapper<>();
        DataObjectConfig doc = queryParam.getQueryParam();
        if(doc.getObjectUniqueId() != null && !doc.getObjectUniqueId().isEmpty()){
            wrapper.eq(DataObject::getDataObjectUniqueId, doc.getObjectUniqueId());
        }
        if(doc.getObjectName() != null && !doc.getObjectName().isEmpty()){
            wrapper.like(DataObject::getName, doc.getObjectName());
        }
        if(doc.getDatabaseId() != null) {
            wrapper.eq(DataObject::getDatasourceId, doc.getDatabaseId());
        }
        //get origin page object
        Page<DataObject> dataObjectPage = dataObjectMapper.selectPage(
                new Page<>(queryParam.getPageNum(), queryParam.getPageSize()),
                wrapper
        );
        //get records from page object
        List<DataObject> records = dataObjectPage.getRecords();
        //transfer its datasourceId to datasource object
        List<DataObjectConfig> collect = records.stream().map(dataObject -> {
            DataObjectConfig config = null;
            try {
                config = DataObjectConfig.readFromPersistent(dataObject);
            } catch (JsonProcessingException e) {
                log.error("转型异常");
                config.setObjectName(dataObject.getName());
                config.setObjectUniqueId(dataObject.getDataObjectUniqueId());
                config.setQueryScript(dataObject.getQueryScript());
                config.setTableName(dataObject.getTableName());
                config.setDatabaseId(dataObject.getDatasourceId());
                Map<String, DataType> fieldMap = new HashMap<>();
                Map<String, String> dataStructure = JacksonUtil.fromJsonToObject(dataObject.getDataStructure(), Map.class);
                dataStructure.entrySet()
                        .forEach(entity -> {
                            fieldMap.put(entity.getKey(), DataType.valueOf(entity.getValue()));
                        });
                config.setFieldMap(fieldMap);
            }
            DataSourceConfig dataSourceConfig = databaseManageService.queryOneByUniqueId(dataObject.getDatasourceId());
            if (dataSourceConfig != null){
                config.setDatasourceName(dataSourceConfig.getName());
            }
            return config;
        }).collect(Collectors.toList());
        //re-package the page object
        Page<DataObjectConfig> page = new Page<>();
        BeanUtils.copyProperties(dataObjectPage, page);
        page.setRecords(collect);
        //and return the re-packaged page object
        return generateSuccessfulResponseObject(page);
    }

    /**
     * @see DataObjectManageService#queryAll()
     */
    @Override
    public GeneralResponse queryAll() {
        List<DataObject> dataObjects = dataObjectMapper.selectList(new LambdaQueryWrapper<DataObject>().isNotNull(DataObject::getDataObjectUniqueId));
        return generateSuccessfulResponseObject(dataObjects);
    }

    private void checkDataObjectIsInUse(String uniqueId) throws CustomException {
        List<DataSet> dataSets =  dataSetMapper.selectList(
                new LambdaQueryWrapper<DataSet>()
                        .like(DataSet::getDataObjects, uniqueId));
        if(!CollectionUtils.isEmpty(dataSets)){
            throw new CustomException(ErrorCode.INVALID_OPERATION, "current data object is in use, can not be modified");
        }
    }
}
