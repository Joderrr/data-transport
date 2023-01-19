package com.stewart.datatransport.exception;

import com.stewart.datatransport.pojo.vo.GeneralResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * global exception handler
 *
 * @author stewart
 * @date 2023/1/20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * handle CustomException, BusinessException, ValidException yet
     *
     * @param exception any type of exception
     * @return  general response
     * @param <E>   any type but extends CustomException
     */
    @ResponseBody
    @ExceptionHandler({CustomException.class, BusinessException.class, ValidException.class})
    public <E extends CustomException> GeneralResponse customExceptionHandler(E exception){
        return GeneralResponse.build(exception.getErrorCode().getCode(), exception.getErrorDescription());
    }

}
