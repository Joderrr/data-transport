package com.stewart.datatransport.enums;

import lombok.Getter;

/**
 * error code enum, list all errors here
 *
 * @author stewart
 * @date 2023/1/18
 */
@Getter
public enum ErrorCode {

    /**
     * operation success
     */
    SUCCESS(200, "operation success!"),

    /**
     * operation failure
     */
    FAILURE(-1, "operation failure!"),

    /**
     * controller's parameter validation error
     */
    PARAMETER_VALID_FAILED(1001, "Parameter Valid Failed"),

    /**
     * invalid operation
     */
    INVALID_OPERATION(1002, "Invalid operation");

    /**
     * constructor method
     *
     * @param code  error code
     * @param message   error description
     */
    ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    /**
     * error code
     */
    final Integer code;

    /**
     * error description(perhaps)
     */
    final String message;



}
