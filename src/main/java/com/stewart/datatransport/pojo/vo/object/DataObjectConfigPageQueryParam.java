package com.stewart.datatransport.pojo.vo.object;

import com.stewart.datatransport.pojo.vo.base.BasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Object configuration's query param
 *
 * @author stewart
 * @date 2023/2/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataObjectConfigPageQueryParam extends BasePageParam {

    DataObjectConfig queryParam;
}
