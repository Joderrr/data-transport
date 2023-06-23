package com.stewart.datatransport.component;

import com.stewart.datatransport.annotation.DBLogic;
import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.logic.DatabaseLogic;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * database basic operation
 * this class managed different database logics by logicMap
 * by using databaseType enum, we can get mysql/oracle logic from logicMap
 * then use DatabaseLogic interface's different implementation to operate the database
 *
 * @author stewart
 * @date 2023/1/18
 */
@Slf4j
@Component
public class DatabaseOperation {

    @Resource
    private ApplicationContext applicationContext;

    private Map<DatabaseType, DatabaseLogic> logicMap = new ConcurrentHashMap<>();

    /**
     * init logicMap, get all beans who annotated by DBLogic.class
     */
    @PostConstruct
    private void initDatabaseOperation() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(DBLogic.class);
        if (!beansWithAnnotation.isEmpty()) {
            beansWithAnnotation.forEach((name, beanInstance) -> {
                if (beanInstance instanceof DatabaseLogic) {
                    logicMap.put(DatabaseType.getDatabaseTypeFromName(name), (DatabaseLogic) beanInstance);
                }
            });
        }
    }

    /**
     * try to connect to database by different type of database logic
     *
     * @param dataSourceConfig database configuration
     * @return connect result
     */
    public ConnectTryResult tryConnection(DataSourceConfig dataSourceConfig) {
        DatabaseLogic databaseLogic = logicMap.get(dataSourceConfig.getDatabaseType());
        return databaseLogic.tryConnection(dataSourceConfig);
    }

    /**
     * before create the data object, user need to make sure they query script will execute correctly
     *
     * @param dataSourceConfig  data source configuration
     * @param script    query script
     * @return  query result
     */
    public List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script){
        DatabaseLogic databaseLogic = logicMap.get(dataSourceConfig.getDatabaseType());
        return databaseLogic.executeQueryScript(dataSourceConfig, script);
    }

    /**
     * execute query script with query conditions
     *
     * @param dataSourceConfig      datasource configuration
     * @param script                query script
     * @param condition             query condition
     * @return                      query result
     */
    public List<Map<String, String>> executeQueryScript(DataSourceConfig dataSourceConfig, String script, Map<String,String> condition){
        DatabaseLogic databaseLogic = logicMap.get(dataSourceConfig.getDatabaseType());
        return databaseLogic.executeQueryScript(dataSourceConfig, script, condition);
    }

}
