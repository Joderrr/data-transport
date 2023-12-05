package com.stewart.datatransport.service;

import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.dataset.DataSetConfig;

/**
 * DataSet Management Service
 * DataSet is a collection of DataObjects.
 *
 * @author stewart
 * @date 2023/6/10
 */
public interface DataSetManageService {

    /**
     * save dataset configuration
     *
     * @param dataSetConfig dataSet configuration
     * @return     save result
     */
    GeneralResponse saveDataSet(DataSetConfig dataSetConfig);

    /**
     * delete dataset configuration
     *
     * @param dataSetUniqueId   dataset uniqueId
     * @return  delete or not
     */
    GeneralResponse deleteDataSet(String dataSetUniqueId);

    /**
     * update data set configuration
     *
     * @param dataSetConfig     dataset configuration
     * @return      update result
     */
    GeneralResponse updateDataSet(DataSetConfig dataSetConfig);

    /**
     * query dataset configurations
     *
     * @param dataSetConfig     query param
     * @return      query result
     */
    GeneralResponse queryDataSet(DataSetConfig dataSetConfig);

    /**
     * query all dataset configurations
     *
     * @return      query result
     */
    GeneralResponse queryAll();

    /**
     * query dataset configuration
     *
     * @param dataSetUniqueId     query param
     * @return      query result
     */
    GeneralResponse queryDataSetDetail(String dataSetUniqueId);

    /**
     * query dataset configuration
     *
     * @param dataSetUniqueId     query param
     * @return      query result
     */
    GeneralResponse queryCondition(String dataSetUniqueId);


}
