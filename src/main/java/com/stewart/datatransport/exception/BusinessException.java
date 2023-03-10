package com.stewart.datatransport.exception;

import com.stewart.datatransport.enums.ErrorCode;
import lombok.Data;

/**
 * Exception will be thrown while customized program is running
 *
 * @author stewart
 * @date 2023/1/18
 */
@Data
public class BusinessException extends CustomException{

    /**
     * constructor methood
     *
     * @param errorCode error code
     * @param errorDescription  error description
     */
    public BusinessException(ErrorCode errorCode, String errorDescription){
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

}
