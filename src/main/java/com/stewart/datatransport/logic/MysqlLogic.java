package com.stewart.datatransport.logic;

import com.stewart.datatransport.annotation.DBLogic;
import com.stewart.datatransport.constant.DatabaseConstants;
import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.pojo.vo.database.AddressAndPort;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfig;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * mysql operation
 *
 * @author stewart
 * @date 2023/1/17
 */
@Component("mysql")
@DBLogic(name = "mysql", type = DatabaseType.MySQL)
public class MysqlLogic implements DatabaseLogic {

    @Override
    public ConnectTryResult tryConnection(DatabaseConfig databaseConfig) {
        boolean executeResult = true;
        String executeMessage = "connect success";
        try {
            List<String> configurationUrls = getConfigurationUrls(databaseConfig);
            for (String configurationUrl : configurationUrls) {
                executeResult = executeResult && connectTest(configurationUrl, databaseConfig.getUsername(), databaseConfig.getPassword());
            }
        } catch (Exception e) {
            executeResult = false;
            executeMessage = "Exception has been thrown while database connection test, exception : " + e.getMessage();
        }
        return new ConnectTryResult(executeResult, executeMessage);
    }

    /**
     * connect test method
     *
     * @param url      database url
     * @param username database username
     * @param password database password
     * @return connect result
     * @throws ClassNotFoundException could be thrown
     * @throws SQLException           could be thrown
     */
    private boolean connectTest(String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(DatabaseConstants.MYSQL_DRIVER_NAME);
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        return statement.execute(DatabaseConstants.MYSQL_CONNECT_TEST_SQL);
    }

    /**
     * get all urls from database configuration's address fields, because there may be a list of cluster's ip and port
     *
     * @param databaseConfig database configuration
     * @return resolved url address
     */
    private List<String> getConfigurationUrls(DatabaseConfig databaseConfig) {
        List<String> urlList = new ArrayList<>();
        String databaseName = databaseConfig.getDatabaseName();
        List<AddressAndPort> addresses = databaseConfig.getAddress();
        for (AddressAndPort address : addresses) {
            urlList.add(urlAssemble(address.getIp(), address.getPort(), databaseName) + DatabaseConstants.MYSQL_URL_PARAMETER_PLACEHOLDER);
        }
        return urlList;
    }

    /**
     * by using ip, port and database name to assemble mysql address's url
     *
     * @param ip           database's ip
     * @param port         database's port
     * @param databaseName database name
     * @return assembled url
     */
    private String urlAssemble(String ip, String port, String databaseName) {
        return DatabaseConstants.MYSQL_URL_PLACEHOLDER
                .replace("{ip}", ip)
                .replace("{port}", port)
                .replace("{databaseName}", databaseName);
    }
}
