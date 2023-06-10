package com.stewart.datatransport.service;

import com.stewart.datatransport.pojo.vo.base.GeneralResponse;

import static com.stewart.datatransport.enums.ErrorCode.FAILURE;
import static com.stewart.datatransport.enums.ErrorCode.SUCCESS;

/**
 * Service basic class, include some general methods for service implementation
 *
 * @author stewart
 * @date 2023/2/16
 */
public class BaseService {

    protected GeneralResponse generateResponseObject(boolean success, Object successContent, Object failContent) {
        if (success) {
            return GeneralResponse.build(SUCCESS.getCode(), SUCCESS.getMessage(), successContent);
        } else {
            return GeneralResponse.build(FAILURE.getCode(), FAILURE.getMessage(), failContent);
        }
    }

    protected GeneralResponse generateResponseObject(boolean success) {
        if (success) {
            return GeneralResponse.build(SUCCESS.getCode(), SUCCESS.getMessage());
        } else {
            return GeneralResponse.build(FAILURE.getCode(), FAILURE.getMessage());
        }
    }

    protected GeneralResponse generateSuccessfulResponseObject(Object content) {
        return GeneralResponse.build(SUCCESS.getCode(), SUCCESS.getMessage());
    }

}
