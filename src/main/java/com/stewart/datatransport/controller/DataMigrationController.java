package com.stewart.datatransport.controller;

import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.migrate.MigrateExportParam;
import com.stewart.datatransport.service.DatabaseManageService;
import com.stewart.datatransport.service.MigrationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * migrate data from database to database
 *
 * @author stewart
 * @date 2023/1/18
 */
@RestController
@RequestMapping("/data/migration")
public class DataMigrationController extends BaseController {

    @Resource
    MigrationService migrationService;

    @Resource
    DatabaseManageService databaseManageService;

    /**
     * export json file
     *
     * @param param         export param
     * @param response      response
     */
    @RequestMapping(method = RequestMethod.POST, value = "/export")
    public void export(@RequestBody MigrateExportParam param, HttpServletResponse response){
         migrationService.generateMigratePackage(param.getDataSetUniqueId(), param.getCondition(), response);
    }

    /**
     * export json file
     *
     * @param multipartFile     import file
     * @param dataSourceId      datasource unique id
     */
    @RequestMapping(method = RequestMethod.POST, value = "/import/{id}")
    public GeneralResponse importData(MultipartFile multipartFile, @PathVariable("id") String dataSourceId){
        return migrationService.importMigrationPackage(
                databaseManageService.queryOne(dataSourceId),
                multipartFile
        );
    }



}
