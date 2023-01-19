package com.stewart.datatransport.controller;

import com.stewart.datatransport.exception.ValidException;
import com.stewart.datatransport.pojo.vo.GeneralResponse;
import com.stewart.datatransport.pojo.vo.database.ConnectTryResult;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfig;
import com.stewart.datatransport.pojo.vo.database.DatabaseConfigPageQueryParam;
import com.stewart.datatransport.service.DatabaseManageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Databases connect management
 *
 * @author stewart
 * @date 2023/1/17
 */
@RestController
@RequestMapping(value = "/database/manage")
public class DatabaseManageController extends BaseController {

    @Resource
    DatabaseManageService databaseManageService;

    /**
     * try connection with offered database config
     *
     * @param databaseConfig database config
     * @return general response with result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/connectTest")
    public GeneralResponse tryDBConnection(@RequestBody DatabaseConfig databaseConfig) throws ValidException {
        parameterValidate(databaseConfig);
        return execute(() -> {
            ConnectTryResult connectTryResult = databaseManageService.tryConnection(databaseConfig);
            return GeneralResponse.build(connectTryResult.isSuccess() ? 200 : -1, connectTryResult.getMessage());
        });
    }

    /**
     * save database configuration
     *
     * @param databaseConfig database configuration
     * @return save result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/saveDbConfig")
    public GeneralResponse saveDatabaseConfig(DatabaseConfig databaseConfig) {
        return execute(() -> databaseManageService.saveDatabaseConfig(databaseConfig));
    }

    /**
     * delete database configuration
     *
     * @param databaseConfig database configuration
     * @return delete result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/deleteDatabaseConfig")
    public GeneralResponse deleteDatabaseConfig(DatabaseConfig databaseConfig) {
        return execute(() -> databaseManageService.deleteDatabaseConfig(databaseConfig));
    }

    /**
     * search database configuration page
     *
     * @param queryParam database config page query param
     * @return query result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/pageQuery")
    public GeneralResponse searchDatabaseConfig(DatabaseConfigPageQueryParam queryParam) {
        return execute(() -> databaseManageService.queryDatabaseConfigPage(queryParam));
    }
}
