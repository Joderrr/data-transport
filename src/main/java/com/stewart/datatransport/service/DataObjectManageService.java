package com.stewart.datatransport.service;

import com.stewart.datatransport.exception.CustomException;
import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfig;
import com.stewart.datatransport.pojo.vo.object.DataObjectConfigPageQueryParam;

/**
 * data object manage service
 *
 * @author stewart
 * @date 2023/2/17
 */
public interface DataObjectManageService {

    /**
     * when user create a data object, they must make sure the query script is works
     *
     * @param configuration data object's configuration
     * @return query result
     */
    GeneralResponse executeQueryScript(DataObjectConfig configuration);

    /**
     * data object save method
     *
     * @param configuration data object's configuration
     * @return save result
     */
    GeneralResponse saveDataObject(DataObjectConfig configuration);

    /**
     * data object update method
     *
     * @param configuration data object's configuration
     * @return update result
     * @throws CustomException dataObject update failed
     */
    GeneralResponse updateDataObject(DataObjectConfig configuration) throws CustomException;

    /**
     * data object delete method
     *
     * @param configuration data object's configuration
     * @return delete result
     */
    GeneralResponse deleteDataObject(DataObjectConfig configuration);

    /**
     * data object page query method
     *
     * @param queryParam data object's query param
     * @return query result
     */
    GeneralResponse queryDataObject(DataObjectConfigPageQueryParam queryParam);

}
