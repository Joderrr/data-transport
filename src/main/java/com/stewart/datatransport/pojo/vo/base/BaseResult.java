package com.stewart.datatransport.pojo.vo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Basic result type
 *
 * @author stewart
 * @date 2023/2/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult {

    /**
     * operation result
     */
    protected boolean success;

    /**
     * description
     */
    protected String message;

}
