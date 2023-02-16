package com.stewart.datatransport.logic;

import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;

/**
 * database logic interface
 *
 * @author stewart
 * @date 2023/1/18
 */
public interface DatabaseLogic {

    /**
     * test database connection
     *
     * @param databaseConfig    database connection configuration
     * @return  connect test result
     */
    ConnectTryResult tryConnection(DataSourceConfig databaseConfig);


}
