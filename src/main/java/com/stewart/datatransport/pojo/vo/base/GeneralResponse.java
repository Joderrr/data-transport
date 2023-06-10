package com.stewart.datatransport.pojo.vo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * general response type
 *
 * @author stewart
 * @date 2023/1/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {

    /**
     * response code
     */
    private int code;

    /**
     * response message
     */
    private String message;

    /**
     * response content
     */
    private Object content;

    /**
     * builder method, but not in builder mode, just gives a list of parameters to assemble a general response object
     *
     * @param code    response code
     * @param message response message
     * @return general response instance
     */
    public static GeneralResponse build(int code, String message) {
        return new GeneralResponse(code, message, null);
    }

    /**
     * builder method, but not in builder mode, just gives a list of parameters to assemble a general response object
     *
     * @param code    response code
     * @param message response message
     * @param content response content
     * @return general response instance
     */
    public static GeneralResponse build(int code, String message, Object content) {
        return new GeneralResponse(code, message, content);
    }
}
