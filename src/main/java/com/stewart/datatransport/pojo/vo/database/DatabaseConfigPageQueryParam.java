package com.stewart.datatransport.pojo.vo.database;

import com.stewart.datatransport.pojo.vo.base.BasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * database configuration page query param
 *
 * @author stewart
 * @date 2023/1/18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DatabaseConfigPageQueryParam extends BasePageParam{

    /**
     * query parameter
     */
    DataSourceConfig queryParam;

}
