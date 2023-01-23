package com.stewart.datatransport.util;

import com.stewart.datatransport.constant.DatabaseConstants;
import com.stewart.datatransport.enums.database.DatabaseType;

import static com.stewart.datatransport.constant.DatabaseConstants.EMPTY_STR;

/**
 * database configuration util
 *
 * @author stewart
 * @date 2023/1/21
 */
public class DatabaseConfigUtil {

    /**
     * by using ip, port and database name to assemble mysql address's url
     *
     * @param databaseType database type
     * @param ip           database's ip
     * @param port         database's port
     * @param databaseName database name
     * @return assembled url
     */
    public static String urlAssemble(DatabaseType databaseType, String ip, String port, String databaseName) {
        switch (databaseType){
            case MySQL:
                return DatabaseConstants.MYSQL_URL_PLACEHOLDER
                        .replace("{ip}", ip)
                        .replace("{port}", port)
                        .replace("{databaseName}", databaseName)
                        + DatabaseConstants.MYSQL_URL_PARAMETER_PLACEHOLDER;
            case Oracle:
            case None:
            default:
                return EMPTY_STR;
        }
    }
}
