package com.stewart.datatransport.pojo.vo.dataset;

import com.stewart.datatransport.pojo.persistent.DataObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * the root relationship object
 *
 * @author stewart
 * @date 2023/6/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataRelations {

    /**
     * root uuid
     * a DataSet need to have a root DataObject
     * root object is the "main method" to this dataSet
     * user need to write a sql script to get the root object
     * once we hit the root, we can use relationship between objects to find more children object
     */
    String rootId;

    /**
     * root object
     */
    DataObject root;

    /**
     * dataObject's relationships from dataSet's configuration.
     */
    List<Relation> children;

}
