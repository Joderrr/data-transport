package com.stewart.datatransport.logic;

import com.stewart.datatransport.annotation.DBLogic;
import com.stewart.datatransport.constant.DatabaseConstants;
import com.stewart.datatransport.enums.ErrorCode;
import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.exception.CustomException;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @see DatabaseLogic#tryConnection(DataSourceConfig)
     */
    @Override
    public ConnectTryResult tryConnection(DataSourceConfig dataSourceConfig) {
        boolean executeResult;
        String executeMessage = "connect success";
        try {
            String configurationUrl = getConfigurationUrls(dataSourceConfig);
            executeResult = connectTest(configurationUrl, dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
        } catch (Exception e) {
            executeResult = false;
            executeMessage = "Exception has been thrown while database connection test, exception : " + e.getMessage();
        }
        return new ConnectTryResult(executeResult, executeMessage);
    }

    @Override
    public List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script) {
        List<Map<String,String>> results = new ArrayList<>();
        try{
            List<String> queryColumn = resolveQueryColumn(script);
            String configurationUrl = getConfigurationUrls(dataSourceConfig);
            Connection connection = getConnection(configurationUrl, dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
            PreparedStatement preparedStatement = connection.prepareStatement(script);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Map<String, String> row = new HashMap<>(16);
                for (String col : queryColumn) {
                    row.put(col, resultSet.getString(col));
                }
                results.add(row);
            }
            return results;
        } catch (CustomException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script, Map<String, String> condition) {
        List<Map<String,String>> results = new ArrayList<>();
        try{
            List<String> queryColumn = resolveQueryColumn(script);
            String configurationUrl = getConfigurationUrls(dataSourceConfig);
            Connection connection = getConnection(configurationUrl, dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
            PreparedStatement preparedStatement = connection.prepareStatement(resolveCondition(script, condition));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Map<String, String> row = new HashMap<>(16);
                for (String col : queryColumn) {
                    row.put(col, resultSet.getString(col));
                }
                results.add(row);
            }
            return results;
        } catch (CustomException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean executeInsertScript(DataSourceConfig dataSourceConfig, String script) {
        List<Map<String,String>> results = new ArrayList<>();
        try{
            List<String> queryColumn = resolveQueryColumn(script);
            String configurationUrl = getConfigurationUrls(dataSourceConfig);
            Connection connection = getConnection(configurationUrl, dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
            PreparedStatement preparedStatement = connection.prepareStatement(script);
            return preparedStatement.execute();
        } catch (CustomException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * connect test method
     *
     * @param url      database url
     * @param username database username
     * @param password database password
     * @return connect result
     * @throws SQLException           could be thrown
     */
    private boolean connectTest(String url, String username, String password) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection(url, username, password);
            Statement statement = connection.createStatement();
            return statement.execute(DatabaseConstants.MYSQL_CONNECT_TEST_SQL);
        }catch (SQLException | CustomException e){
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
    private String getConfigurationUrls(DataSourceConfig databaseConfig) {
        String databaseName = databaseConfig.getDatabaseName();
        return urlAssemble(databaseConfig.getDatabaseType(), databaseConfig.getAddress(), databaseConfig.getPort(), databaseName);
    }

    private Connection getConnection(String url, String username, String password) throws CustomException {
        try {
            Class.forName(DatabaseConstants.MYSQL_DRIVER_NAME);
            return DriverManager.getConnection(url, username, password);
        }catch (ClassNotFoundException | SQLException e){
            log.error("database connect failed, because of {}", e.getMessage());
            throw new CustomException(ErrorCode.DATABASE_CONNECT_FAILED, "try to get database connection failed");
        }
    }

    private List<String> resolveQueryColumn(String queryScript){
        List<String> columns = new ArrayList<>();
        String[] splitColumns = queryScript.split(",");
        for (int i = 0; i < splitColumns.length; i++) {
            if(i == 0){
                columns.add(splitColumns[i].replace("select", "").replaceAll(" ",""));
            } else if(i == splitColumns.length - 1) {
                //the last column are beside of the 'from', so we replace all whitespaces, then split by from and get first
                columns.add(splitColumns[i].replaceAll(" ","").split("from")[0]);
            } else {
                columns.add(splitColumns[i].replaceAll(" ",""));
            }
        }
        return columns;
    }

    private String resolveCondition(String queryScript, Map<String, String> condition){
        String tempSql = queryScript;
        for (Map.Entry<String, String> entry : condition.entrySet()) {
            String replaceKey = "{" + entry.getKey() + "}";
            tempSql = queryScript.replace(replaceKey, entry.getValue());
        }
        return tempSql;
    }
}
