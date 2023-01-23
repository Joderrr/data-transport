package com.stewart.datatransport.logic;

import com.stewart.datatransport.annotation.DBLogic;
import com.stewart.datatransport.constant.DatabaseConstants;
import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.pojo.vo.database.AddressAndPort;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.stewart.datatransport.util.DatabaseConfigUtil.urlAssemble;

/**
 * mysql operation
 *
 * @author stewart
 * @date 2023/1/17
 */
@Slf4j
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
    private boolean connectTest(String url, String username, String password) throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DatabaseConstants.MYSQL_DRIVER_NAME);
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            return statement.execute(DatabaseConstants.MYSQL_CONNECT_TEST_SQL);
        }catch (ClassNotFoundException | SQLException e){
            log.error("database connection test failed, because of {}", e.getMessage());
            return false;
        }finally {
            if(connection != null){
                connection.close();
            }
        }
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
            urlList.add(urlAssemble(databaseConfig.getDatabaseType(), address.getIp(), address.getPort(), databaseName));
        }
        return urlList;
    }
}
