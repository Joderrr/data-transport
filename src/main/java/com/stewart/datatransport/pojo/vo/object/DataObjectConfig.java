package com.stewart.datatransport.pojo.vo.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stewart.datatransport.enums.object.DataType;
import com.stewart.datatransport.pojo.persistent.DataObject;
import com.stewart.datatransport.util.JacksonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    String databaseId;

    String tableName;

    /**
     * data object query condition
     */
    List<String> queryCondition;

    /**
     * database's query sql script
     */
    String queryScript;

    /**
     * fields from the result of query script
     */
    Map<String, DataType> fieldMap;

    /**
     * datasourceName
     */
    String datasourceName;

    public DataObject toPersistent() {
        return DataObject.builder()
                .name(objectName)
                .dataObjectUniqueId(objectUniqueId)
                .datasourceId(databaseId)
                .tableName(tableName)
                .queryCondition(JacksonUtil.toJsonString(queryCondition))
                .queryScript(queryScript)
                .dataStructure(JacksonUtil.toJsonString(fieldMap))
                .build();
    }

    public static DataObjectConfig readFromPersistent(DataObject dataObject) throws JsonProcessingException {
        return DataObjectConfig.builder()
                .databaseId(dataObject.getDatasourceId())
                .objectUniqueId(dataObject.getDataObjectUniqueId())
                .fieldMap(new ObjectMapper().readValue(dataObject.getDataStructure(), new TypeReference<Map<String, DataType>>() {
                }))
                .queryCondition(new ObjectMapper().readValue(dataObject.getQueryCondition(), new TypeReference<List<String>>() {
                }))
                .queryScript(dataObject.getQueryScript())
                .objectName(dataObject.getName())
                .tableName(dataObject.getTableName())
                .build();
    }


}
