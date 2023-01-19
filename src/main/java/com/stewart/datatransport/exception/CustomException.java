package com.stewart.datatransport.exception;

import com.stewart.datatransport.enums.ErrorCode;
import lombok.Data;

/**
 * @author stewart
 * @date 2023/1/20
 */
@Data
public class CustomException extends Exception{

    /**
     * errorCode
     */
    protected ErrorCode errorCode;

    /**
     * error description
     */
    protected String errorDescription;
}
