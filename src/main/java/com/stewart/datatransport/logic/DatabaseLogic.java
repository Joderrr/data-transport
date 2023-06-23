package com.stewart.datatransport.logic;

import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;

import java.util.List;
import java.util.Map;

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
     * @param dataSourceConfig    database connection configuration
     * @return  connect test result
     */
    ConnectTryResult tryConnection(DataSourceConfig dataSourceConfig);

    /**
     * execute query script of data object
     *
     * @param dataSourceConfig  database connection configuration
     * @param script    query script
     * @return  query result
     */
    List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script);


    /**
     * execute query script of data, with the condition map
     *
     * @param dataSourceConfig  database connection configuration
     * @param script            query script
     * @param condition         query condition
     * @return                  query result
     */
    List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script, Map<String, String> condition);

}
