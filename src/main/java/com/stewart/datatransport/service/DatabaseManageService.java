package com.stewart.datatransport.service;

import com.stewart.datatransport.pojo.vo.GeneralResponse;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfig;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfigPageQueryParam;

/**
 * database manage service interface
 *
 * @author stewart
 * @date 2023/1/17
 */
public interface DatabaseManageService {

    /**
     * try connection with offered database config
     *
     * @param databaseConfig database config
     * @return connection try result
     */
    ConnectTryResult tryConnection(DatabaseConfig databaseConfig);

    /**
     * save database configuration
     *
     * @param databaseConfig database config
     * @return database config save result (success or not)
     */
    GeneralResponse saveDatabaseConfig(DatabaseConfig databaseConfig);

    /**
     * delete database configuration
     *
     * @param databaseConfig database config
     * @return database config delete result (success or not)
     */
    GeneralResponse deleteDatabaseConfig(DatabaseConfig databaseConfig);

    /**
     * page query for database configuration
     *
     * @param queryParam database config page query param
     * @return query result
     */
    GeneralResponse queryDatabaseConfigPage(DatabaseConfigPageQueryParam queryParam);

}
