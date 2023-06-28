package com.stewart.datatransport.pojo.vo.migrate;

import lombok.Data;

import java.util.Map;

/**
 * migration param
 *
 * @author stewart
 * @date 2023/6/29
 */
@Data
public class MigrateExportParam {

    /**
     * dataset unique id
     */
    String dataSetUniqueId;

    /**
     * condition map
     */
    Map<String, String> condition;
}
