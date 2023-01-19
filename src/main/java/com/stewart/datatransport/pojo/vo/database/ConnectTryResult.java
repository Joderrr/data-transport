package com.stewart.datatransport.pojo.vo.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * database connect try result
 *
 * @author stewart
 * @date 2023/1/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectTryResult {

    /**
     * connect result
     */
    private boolean success;

    /**
     * success or fail description
     */
    private String message;
}
