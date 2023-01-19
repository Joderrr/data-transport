package com.stewart.datatransport.controller;

import com.stewart.datatransport.pojo.vo.GeneralResponse;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * data object is made of a serial of query conditions and database config
 *
 * @author stewart
 * @date 2023/1/18
 */
@RestController
@RequestMapping("/data/manage")
public class DataObjectManageController {

    public GeneralResponse executeSqlScript(DataObjectConfig config){
        return null;
    }
}
