package com.stewart.datatransport.controller;

import com.stewart.datatransport.exception.CustomException;
import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfig;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfigPageQueryParam;
import com.stewart.datatransport.service.DataObjectManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.stewart.datatransport.enums.ErrorCode.INVALID_OPERATION;

/**
 * data object is made of a serial of query conditions and database config
 *
 * @author stewart
 * @date 2023/1/18
 */
@Slf4j
@RestController
@RequestMapping("/data/manage")
public class DataObjectManageController extends BaseController {

    @Resource
    DataObjectManageService dataObjectManageService;

    /**
     * execute the query script, get the result, then fill up the fieldMap
     *
     * @param config    dataObject's configuration
     * @return          execute result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/executeQueryScript")
    public GeneralResponse executeQueryScript(@RequestBody DataObjectConfig config){
        return execute(() -> dataObjectManageService.executeQueryScript(config));
    }

    /**
     * save data object, I mean its configuration
     *
     * @param config    dataObject's configuration
     * @return          save result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/saveDataObject")
    public GeneralResponse saveConfiguration(@RequestBody DataObjectConfig config){
        return execute(() -> dataObjectManageService.saveDataObject(config));
    }

    /**
     * update data object's configuration
     *
     * @param config    data object's configuration
     * @return          update result
     * @throws CustomException  data is in use, can not be modified
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/updateDataObject")
    public GeneralResponse updateConfiguration(@RequestBody DataObjectConfig config) {
        return execute(() -> {
            try {
                return dataObjectManageService.updateDataObject(config);
            } catch (CustomException e) {
               log.error("data is in use, can not be modified, data unique id = {}", config.getObjectUniqueId());
               return GeneralResponse.build(INVALID_OPERATION.getCode(), "data is in use, can not be modified");
            }
        });
    }

    /**
     * delete data object's configuration
     *
     * @param config    data object's configuration, id is most important
     * @return          delete result
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteDataObject")
    public GeneralResponse deleteConfiguration(@RequestBody DataObjectConfig config){
        return execute(() -> dataObjectManageService.deleteDataObject(config));
    }

    /**
     * query data object configurations
     *
     * @param param     query params
     * @return          query result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/queryDataObjects")
    public GeneralResponse queryConfiguration(@RequestBody DataObjectConfigPageQueryParam param){
        return execute(() -> dataObjectManageService.queryDataObject(param));
    }

    /**
     * query data object configurations
     *
     * @return          query result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/queryAll")
    public GeneralResponse queryAll(){
        return execute(() -> dataObjectManageService.queryAll());
    }


}
