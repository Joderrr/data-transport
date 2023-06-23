package com.stewart.datatransport.logic;

import com.stewart.datatransport.annotation.DBLogic;
import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * oracle operation, not implemented
 *
 * @author stewart
 * @date 2023/1/17
 */
@Component("oracle")
@DBLogic(name = "oracle", type = DatabaseType.Oracle)
public class OracleLogic implements DatabaseLogic {
    @Override
    public ConnectTryResult tryConnection(DataSourceConfig databaseConfig) {
        return null;
    }

    @Override
    public List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script) {
        return null;
    }

    @Override
    public List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script, Map<String, String> condition) {
        return null;
    }
}
