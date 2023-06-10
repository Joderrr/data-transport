package com.stewart.datatransport.pojo.vo.database;

import com.stewart.datatransport.pojo.vo.base.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * database connect try result
 *
 * @author stewart
 * @date 2023/1/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ConnectTryResult extends BaseResult {

    public ConnectTryResult(boolean success, String message){
        super.success = success;
        super.message = message;
    }

}
