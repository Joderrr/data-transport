package com.stewart.datatransport.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stewart.datatransport.component.DatabaseOperation;
import com.stewart.datatransport.enums.ErrorCode;
import com.stewart.datatransport.exception.CustomException;
import com.stewart.datatransport.mapper.DataObjectMapper;
import com.stewart.datatransport.mapper.DatabaseConfigMapper;
import com.stewart.datatransport.pojo.persistent.DataObject;
import com.stewart.datatransport.pojo.persistent.DatabaseConfig;
import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfigPageQueryParam;
import com.stewart.datatransport.service.BaseService;
import com.stewart.datatransport.service.DatabaseManageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * database manage service implementation
 *
 * @author stewart
 * @date 2023/1/18
 */
@Service
public class DatabaseManageServiceImpl extends BaseService implements DatabaseManageService {

    @Resource
    DatabaseOperation databaseOperation;

    @Resource
    DatabaseConfigMapper databaseConfigMapper;

    @Resource
    DataObjectMapper dataObjectMapper;

    /**
     * @see DatabaseManageService#tryConnection(DataSourceConfig)
     */
    @Override
    public ConnectTryResult tryConnection(DataSourceConfig databaseConfig) {
        return databaseOperation.tryConnection(databaseConfig);
    }

    /**
     * @see DatabaseManageService#saveDatabaseConfig(DataSourceConfig)
     */
    @Override
    public GeneralResponse saveDatabaseConfig(DataSourceConfig dataSourceConfig) {
        dataSourceConfig.setDatabaseUniqueId(generateUuid());
        int insert = databaseConfigMapper.insert(dataSourceConfig.toPersistent());
        return generateResponseObject(insert > 0, dataSourceConfig, "datasource insert failure");
    }

    /**
     * @see DatabaseManageService#deleteDatabaseConfig(DataSourceConfig)
     */
    @Override
    public GeneralResponse deleteDatabaseConfig(DataSourceConfig databaseConfig) {
        int delete = databaseConfigMapper.delete(new LambdaQueryWrapper<DatabaseConfig>().eq(DatabaseConfig::getDatabaseUniqueId, databaseConfig.getDatabaseUniqueId()));
        return generateResponseObject(delete > 0, "datasource delete success", "datasource delete failure");
    }

    /**
     * @see DatabaseManageService#updateDatabaseConfig(DataSourceConfig)
     */
    @Override
    public GeneralResponse updateDatabaseConfig(DataSourceConfig databaseConfig) throws CustomException {
        checkDatasourceIsInUse(databaseConfig.getDatabaseUniqueId());
        int update = databaseConfigMapper.update(databaseConfig.toPersistent(), new LambdaQueryWrapper<DatabaseConfig>().eq(DatabaseConfig::getDatabaseUniqueId, databaseConfig.getDatabaseUniqueId()));
        return generateResponseObject(update > 0, databaseConfig, "datasource update failure");
    }

    /**
     * @see DatabaseManageService#queryDatabaseConfigPage(DatabaseConfigPageQueryParam)
     */
    @Override
    public GeneralResponse queryDatabaseConfigPage(DatabaseConfigPageQueryParam queryParam) {
        DatabaseConfig param = queryParam.getQueryParam().toQuery();
        Page<DatabaseConfig> databaseConfigPage = databaseConfigMapper.selectPage(
                new Page<>(queryParam.getPageNum(), queryParam.getPageSize()),
                new LambdaQueryWrapper<DatabaseConfig>()
                        .like(param.getAddress() != null && !param.getAddress().isEmpty(), DatabaseConfig::getDatabaseUniqueId, param.getDatabaseUniqueId())
                        .like(param.getName() != null && param.getName().isEmpty(), DatabaseConfig::getName, param.getName())
        );
        return generateSuccessfulResponseObject(databaseConfigPage);
    }

    /**
     * check if database configuration is in use, throw a customer exception
     *
     * @param databaseConfigUniqueId database configuration uuid
     * @throws CustomException throws only when database in use
     */
    private void checkDatasourceIsInUse(String databaseConfigUniqueId) throws CustomException {
        DatabaseConfig databaseConfig = databaseConfigMapper.selectOne(
                new LambdaQueryWrapper<DatabaseConfig>()
                        .eq(DatabaseConfig::getDatabaseUniqueId, databaseConfigUniqueId)
        );
        List<DataObject> dataObjects = dataObjectMapper.selectList(
                new LambdaQueryWrapper<DataObject>()
                        .eq(DataObject::getDatasourceId, databaseConfig.getId())
        );
        if (!CollectionUtils.isEmpty(dataObjects)) {
            throw new CustomException(ErrorCode.INVALID_OPERATION, "current datasource is in use, can not be modified");
        }
    }

    /**
     * @see DatabaseManageService#queryOne(String)
     */
    @Override
    public DataSourceConfig queryOne(String uniqueId) {
        return DataSourceConfig.readFromPersistent(
                databaseConfigMapper.selectOne(
                        new LambdaQueryWrapper<DatabaseConfig>()
                                .eq(DatabaseConfig::getDatabaseUniqueId, uniqueId)
                )
        );
    }
}
