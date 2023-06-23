package com.stewart.datatransport.pojo.vo.dataset;

import com.stewart.datatransport.pojo.persistent.DataSet;
import com.stewart.datatransport.util.JacksonUtil;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DataSet configuration class,
 *
 * @author stewart
 * @date 2023/6/11
 */
@Data
@Builder
public class DataSetConfig {

    /**
     * data set name
     */
    String name;

    /**
     * data set unique id, like uuid
     */
    String dataSetUniqueId;

    /**
     * data objects id collections of data set
     * include all data objects id, from root to the leaf.
     */
    List<String> dataObjectUniqueIds;

    /**
     * data relations, which maintains a root node and a root query script,
     * from here we can use data-relation's children(relation) to find all data like a tree
     */
    DataRelations dataRelations;

    public DataSet toPersistent() {
        return DataSet.builder()
                .dataSetUniqueId(dataSetUniqueId)
                .dataObjects(JacksonUtil.toJsonString(dataObjectUniqueIds))
                .name(name)
                .metadata(JacksonUtil.toJsonString(dataRelations))
                .build();
    }

    public static DataSetConfig readFromPersistent(DataSet dataSet) {
        return DataSetConfig.builder()
                .dataSetUniqueId(dataSet.getDataSetUniqueId())
                .dataRelations(JacksonUtil.fromJsonToObject(dataSet.getMetadata(), DataRelations.class))
                .name(dataSet.getName())
                .dataObjectUniqueIds(JacksonUtil.fromJsonToObject(dataSet.getDataObjects(), List.class))
                .build();
    }
}
