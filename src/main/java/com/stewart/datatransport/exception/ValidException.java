package com.stewart.datatransport.exception;

import com.stewart.datatransport.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * validate exception
 *
 * @author stewart
 * @date 2023/1/19
 */
@Data
public class ValidException extends CustomException {

    /**
     * constructor methood
     *
     * @param errorCode error code
     * @param errorDescription  error description
     */
    public ValidException(ErrorCode errorCode, String errorDescription){
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
