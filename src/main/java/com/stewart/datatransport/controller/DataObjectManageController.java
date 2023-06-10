package com.stewart.datatransport.controller;

import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfig;
import com.stewart.datatransport.service.DataObjectManageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * data object is made of a serial of query conditions and database config
 *
 * @author stewart
 * @date 2023/1/18
 */
@RestController
@RequestMapping("/data/manage")
public class DataObjectManageController {

    @Resource
    DataObjectManageService dataObjectManageService;

    /**
     * execute the query script, get the result, then fill up the fieldMap
     *
     * @param config    dataObject's configuration
     * @return          execute result
     */
    @RequestMapping("/execute")
    public GeneralResponse executeQueryScript(DataObjectConfig config){
        return dataObjectManageService.executeQueryScript(config);
    }

    @RequestMapping("/save")
    public GeneralResponse saveConfiguration(DataObjectConfig config){
        return dataObjectManageService.saveDataObject(config);
    }



}
