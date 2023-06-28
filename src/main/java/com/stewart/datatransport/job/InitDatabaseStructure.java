package com.stewart.datatransport.job;

import com.stewart.datatransport.constant.DatabaseConstants;
import com.stewart.datatransport.pojo.config.DataSourceConfiguration;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * init database and table structure
 * according to configuration of application.yml, dataSource.initDatabaseIfNotExist
 * if initDatabaseIfNotExist config values true, once program has running successful,
 * database and table will create automatically
 *
 * Not important, delay implement, all code's optimize should after graduate.
 *
 * @author stewart
 * @date 2023/1/21
 */
@Slf4j
//@Component
public class InitDatabaseStructure {

    @Resource
    DataSourceConfiguration dataSourceConfiguration;

    //@PostConstruct
    public void initDatabaseStructure() throws SQLException {
        if(dataSourceConfiguration.isInitDatabaseIfNotExist()){
            Connection connection = null;
            try{
                Driver driver = DriverManager.getDriver(dataSourceConfiguration.getUrl());
                if(driver == null){
                    switch (dataSourceConfiguration.getDatabaseType()){
                            case MySQL:
                                Class.forName(DatabaseConstants.MYSQL_DRIVER_NAME);
                            case Oracle:
                            case None:
                            default:
                                break;
                        }
                }
                connection = DriverManager.getConnection(
                        dataSourceConfiguration.getUrl(),
                        dataSourceConfiguration.getUsername(),
                        dataSourceConfiguration.getPassword()
                );
                checkDatabaseExistOrCreate(connection);
                useDB(connection);
                // todo : read script file of database and check table, if not exist, create table structure
                //checkTableExistOrCreate(connection, );
            }catch (ClassNotFoundException | SQLException e){
                log.error("database init error, {}", e.getMessage());
            }finally {
                if(connection != null){
                    connection.close();
                }
            }
        }
    }

    private void checkDatabaseExistOrCreate(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dataSourceConfiguration.getDatabaseQuerySQL());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                log.info("database {} exist", dataSourceConfiguration.getDatabaseName());
            } else {
                preparedStatement = connection.prepareStatement(dataSourceConfiguration.getDatabaseCreateSQL());
                preparedStatement.executeUpdate();
                log.info("database doesn't exist, create database {}", dataSourceConfiguration.getDatabaseName());
            }

        }catch (SQLException e){
            log.error("database check error, {}", e.getMessage());
            throw e;
        }finally {
            if(preparedStatement != null){
                preparedStatement.close();
            }
        }
    }

    private void useDB(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(dataSourceConfiguration.getUseDatabaseSQL());
        preparedStatement.execute();
    }


}
