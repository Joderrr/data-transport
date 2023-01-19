package com.stewart.datatransport.service.implement;

import com.stewart.datatransport.component.DatabaseOperation;
import com.stewart.datatransport.pojo.vo.GeneralResponse;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfig;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfigPageQueryParam;
import com.stewart.datatransport.service.DatabaseManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * database manage service implementation
 *
 * @author stewart
 * @date 2023/1/18
 */
@Service
public class DatabaseManageServiceImpl implements DatabaseManageService {

    @Resource
    DatabaseOperation databaseOperation;

    /**
     * @see DatabaseManageService#tryConnection(DatabaseConfig)
     */
    @Override
    public ConnectTryResult tryConnection(DatabaseConfig databaseConfig) {
        return databaseOperation.tryConnection(databaseConfig);
    }

    /**
     * @see DatabaseManageService#saveDatabaseConfig(DatabaseConfig)
     */
    @Override
    public GeneralResponse saveDatabaseConfig(DatabaseConfig databaseConfig) {
        return null;
    }

    /**
     * @see DatabaseManageService#deleteDatabaseConfig(DatabaseConfig)
     */
    @Override
    public GeneralResponse deleteDatabaseConfig(DatabaseConfig databaseConfig) {
        return null;
    }

    /**
     * @see DatabaseManageService#queryDatabaseConfigPage(DatabaseConfigPageQueryParam)
     */
    @Override
    public GeneralResponse queryDatabaseConfigPage(DatabaseConfigPageQueryParam queryParam) {
        return null;
    }
}
