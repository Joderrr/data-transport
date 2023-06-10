package com.stewart.datatransport.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stewart.datatransport.component.DatabaseOperation;
import com.stewart.datatransport.enums.ErrorCode;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * data object management service's implementation
 *
 * @author stewart
 * @date 2023/2/17
 */
@Service
public class DataObjectManageServiceImpl extends BaseService implements DataObjectManageService {

    @Resource
    DatabaseOperation databaseOperation;

    @Resource
    DatabaseConfigMapper databaseConfigMapper;

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
                        .eq(DatabaseConfig::getId, configuration.getDatabaseId())
        ));
        List<Map<String, String>> result =  databaseOperation.executeQueryScript(dataSourceConfig, configuration.getQueryScript());
        return generateSuccessfulResponseObject(result);
    }

    /**
     * @see DataObjectManageService#saveDataObject(DataObjectConfig)
     */
    @Override
    public GeneralResponse saveDataObject(DataObjectConfig configuration) {
        int insert = dataObjectMapper.insert(configuration.toPersistent());
        return generateResponseObject(insert > 0, "data object insert success", "data object insert failure");
    }

    /**
     * @see DataObjectManageService#updateDataObject(DataObjectConfig)
     */
    @Override
    public GeneralResponse updateDataObject(DataObjectConfig configuration) throws CustomException {
        checkDataObjectIsInUse(configuration.getObjectUniqueId());
        int update = dataObjectMapper.update(configuration.toPersistent(), new LambdaQueryWrapper<DataObject>().eq(DataObject::getDataObjectUniqueId, configuration.getObjectUniqueId()));
        return generateResponseObject(update > 0, "data object update success", "data object update failure");
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
        if(doc.getObjectUniqueId() != null){
            wrapper.eq(DataObject::getDataObjectUniqueId, doc.getObjectUniqueId());
        }
        if(doc.getObjectName() != null){
            wrapper.like(DataObject::getName, doc.getObjectName());
        }
        if(doc.getDatabaseId() != null) {
            wrapper.eq(DataObject::getDatasourceId, doc.getDatabaseId());
        }
        return generateSuccessfulResponseObject(dataObjectMapper.selectList(wrapper));
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
