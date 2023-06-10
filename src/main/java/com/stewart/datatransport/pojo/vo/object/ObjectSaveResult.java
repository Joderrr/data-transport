package com.stewart.datatransport.pojo.vo.object;

import com.stewart.datatransport.pojo.vo.base.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * object save result
 *
 * @author stewart
 * @date 2023/2/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ObjectSaveResult extends BaseResult {

    public ObjectSaveResult(boolean success, String message){
        super.success = success;
        super.message = message;
    }

}
