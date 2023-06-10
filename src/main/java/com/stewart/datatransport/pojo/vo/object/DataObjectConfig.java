package com.stewart.datatransport.pojo.vo.object;

import com.stewart.datatransport.enums.object.DataType;
import com.stewart.datatransport.pojo.persistent.DataObject;
import com.stewart.datatransport.util.JacksonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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
     * related database's id
     */
    Long databaseId;

    /**
     * database's query sql script
     */
    String queryScript;

    /**
     * fields from the result of query script
     */
    Map<String, DataType> fieldMap;

    public DataObject toPersistent() {
        return DataObject.builder()
                .name(objectName)
                .dataObjectUniqueId(objectUniqueId)
                .datasourceId(databaseId)
                .queryScript(queryScript)
                .dataStructure(JacksonUtil.toJsonString(fieldMap))
                .build();
    }

    public static DataObjectConfig readFromPersistent(DataObject dataObject){
        return DataObjectConfig.builder()
                .databaseId(dataObject.getDatasourceId())
                .objectUniqueId(dataObject.getDataObjectUniqueId())
                .fieldMap(JacksonUtil.fromJsonToObject(dataObject.getDataStructure(), Map.class))
                .queryScript(dataObject.getQueryScript())
                .objectName(dataObject.getName())
                .build();
    }


}
