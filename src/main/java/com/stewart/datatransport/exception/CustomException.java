package com.stewart.datatransport.exception;

import com.stewart.datatransport.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stewart
 * @date 2023/1/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CustomException extends Exception {

    /**
     * errorCode
     */
    protected ErrorCode errorCode;

    /**
     * error description
     */
    protected String errorDescription;
}
