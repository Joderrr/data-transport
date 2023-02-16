package com.stewart.datatransport.service;

import com.stewart.datatransport.exception.CustomException;
import com.stewart.datatransport.pojo.vo.GeneralResponse;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
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
    ConnectTryResult tryConnection(DataSourceConfig databaseConfig);

    /**
     * save database configuration
     *
     * @param databaseConfig database config
     * @return database config save result (success or not)
     */
    GeneralResponse saveDatabaseConfig(DataSourceConfig databaseConfig);

    /**
     * delete database configuration
     *
     * @param databaseConfig database config
     * @return database config delete result (success or not)
     */
    GeneralResponse deleteDatabaseConfig(DataSourceConfig databaseConfig);

    /**
     * update database configuration
     *
     * @param databaseConfig    database config
     * @return  database config update result (success or not)
     * @throws CustomException before update, check datasource is in use or not
     */
    GeneralResponse updateDatabaseConfig(DataSourceConfig databaseConfig) throws CustomException;

    /**
     * page query for database configuration
     *
     * @param queryParam database config page query param
     * @return query result
     */
    GeneralResponse queryDatabaseConfigPage(DatabaseConfigPageQueryParam queryParam);

}
