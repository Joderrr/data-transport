package com.stewart.datatransport.pojo.vo;

import lombok.Data;

/**
 * base page query param, include page-size and page-num
 *
 * @author stewart
 * @date 2023/1/18
 */
@Data
public class BasePageParam {

    /**
     * single page size
     */
    int pageSize;

    /**
     * current page num
     */
    int pageNum;
}
