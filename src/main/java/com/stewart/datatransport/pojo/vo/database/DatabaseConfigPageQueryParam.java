package com.stewart.datatransport.pojo.vo.database;

import com.stewart.datatransport.pojo.vo.BasePageParam;
import lombok.Data;

/**
 * database configuration page query param
 *
 * @author stewart
 * @date 2023/1/18
 */
@Data
public class DatabaseConfigPageQueryParam extends BasePageParam{

    /**
     * query parameter
     */
    DataSourceConfig queryParam;

}
