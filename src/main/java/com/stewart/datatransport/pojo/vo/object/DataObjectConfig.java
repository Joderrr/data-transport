package com.stewart.datatransport.pojo.vo.object;

import com.stewart.datatransport.enums.object.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * data object configuration
 *
 * @author stewart
 * @date 2023/1/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataObjectConfig {

    /**
     * object name
     */
    String objectName;

    /**
     * unique id, generally use uuid
     */
    String objectUniqueId;

    /**
     * related database's unique id
     */
    String databaseUniqueId;

    /**
     * database's query sql script
     */
    String queryScript;

    /**
     * fields from the result of query script
     */
    Map<String, DataType> fieldMap;

}
