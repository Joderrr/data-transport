package com.stewart.datatransport.controller;

import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.dataset.DataSetConfig;
import com.stewart.datatransport.service.DataSetManageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * data set management
 *
 * @author stewart
 * @date 2023/1/18
 */
@RestController
@RequestMapping("/dataset/manage")
public class DataSetManageController extends BaseController {

    @Resource
    DataSetManageService dataSetManageService;

    /**
     * save dataset configuration
     *
     * @param dataSetConfig    dataSet Configuration
     * @return     save result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/saveDataSet")
    public GeneralResponse saveDataSet(@RequestBody DataSetConfig dataSetConfig){
        return execute(() -> dataSetManageService.saveDataSet(dataSetConfig));
    }

    /**
     * Delete DataSet configuration
     *
     * @param dataSetUniqueId  delete DataSet configuration
     * @return    delete result
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteDataSet")
    public GeneralResponse deleteDataSet(String dataSetUniqueId){
        return execute(() -> dataSetManageService.deleteDataSet(dataSetUniqueId));
    }

    /**
     * update dataSet configuration
     *
     * @param dataSetConfig     dataSet configuration
     * @return      update result
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/updateDataSet")
    public GeneralResponse updateDataSet(@RequestBody DataSetConfig dataSetConfig){
        return execute(() -> dataSetManageService.updateDataSet(dataSetConfig));
    }

    /**
     * query dataset by dataset configuration
     *
     * @param dataSetConfig   dataSet query param
     * @return  query result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/queryDataSets")
    public GeneralResponse queryDataSet(@RequestBody DataSetConfig dataSetConfig){
        return execute(() -> dataSetManageService.queryDataSet(dataSetConfig));
    }

    /**
     * query dataset by dataset configuration
     *
     * @return  query result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/queryAll")
    public GeneralResponse queryDataSet(){
        return execute(() -> dataSetManageService.queryAll());
    }

    /**
     * query dataset by dataset configuration
     *
     * @param dataSetUniqueId   dataSet query param
     * @return  query result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dataSetDetail/{dataSetUniqueId}")
    public GeneralResponse dataSetDetail(@PathVariable String dataSetUniqueId){
        return execute(() -> dataSetManageService.queryDataSetDetail(dataSetUniqueId));
    }

    /**
     * query dataset by dataset configuration
     *
     * @param dataSetUniqueId   dataSet query param
     * @return  query result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/condition/{dataSetUniqueId}")
    public GeneralResponse queryCondition(@PathVariable String dataSetUniqueId){
        return execute(() -> dataSetManageService.queryCondition(dataSetUniqueId));
    }
}
