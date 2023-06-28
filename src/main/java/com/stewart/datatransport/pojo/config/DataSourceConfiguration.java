package com.stewart.datatransport.pojo.config;

import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.util.DatabaseConfigUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.stewart.datatransport.constant.DatabaseConstants.EMPTY_STR;

/**
 * program dependent database configuration
 *
 * @author stewart
 * @date 2023/1/21
 */
@Getter
@Setter
//@Component
@ConfigurationProperties(prefix = "dataSource")
public class DataSourceConfiguration {

    /**
     * database type, mysql/oracle supported
     */
    private DatabaseType databaseType;

    /**
     * database ip
     */
    private String ip;

    /**
     * database port
     */
    private String port;

    /**
     * database name
     */
    private String databaseName;

    /**
     * database username
     */
    private String username;

    /**
     * database password
     */
    private String password;

    /**
     * database init config
     * if true, when database doesn't exist, will create database and table
     */
    private boolean initDatabaseIfNotExist;

    public String getUrl(){
        return DatabaseConfigUtil.urlAssemble(databaseType, ip, port, EMPTY_STR);
    }

    public String getDatabaseQuerySQL(){
        return "SHOW DATABASE LIKE \"" + databaseName + "\"";
    }

    public String getDatabaseCreateSQL(){
        return "CREATE DATABASE " + databaseName;
    }

    public String getUseDatabaseSQL(){
        return "USE " + databaseName;
    }

}
