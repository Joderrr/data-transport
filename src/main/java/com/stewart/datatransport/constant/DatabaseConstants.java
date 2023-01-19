package com.stewart.datatransport.constant;

/**
 * constants of whole circle of database operations
 *
 * @author stewart
 * @date 2023/1/19
 */
public class DatabaseConstants {

    public static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public static final String MYSQL_URL_PLACEHOLDER = "jdbc:mysql://{ip}:{port}/{databaseName}";

    public static final String MYSQL_URL_PARAMETER_PLACEHOLDER = "?useSSL=false&serverTimezone=UTC";

    public static final String MYSQL_CONNECT_TEST_SQL = "select 1 from dual";
}
