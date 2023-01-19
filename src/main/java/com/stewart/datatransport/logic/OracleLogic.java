package com.stewart.datatransport.logic;

import com.stewart.datatransport.annotation.DBLogic;
import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfig;
import org.springframework.stereotype.Component;

/**
 * oracle operation
 *
 * @author stewart
 * @date 2023/1/17
 */
@Component("oracle")
@DBLogic(name = "oracle", type = DatabaseType.Oracle)
public class OracleLogic implements DatabaseLogic {
    @Override
    public ConnectTryResult tryConnection(DatabaseConfig databaseConfig) {
        return null;
    }
}
