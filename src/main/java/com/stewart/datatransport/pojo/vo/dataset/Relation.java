package com.stewart.datatransport.pojo.vo.dataset;

import com.stewart.datatransport.pojo.persistent.DataObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * relation child of data-relations
 * data relations has a root object and a query script
 * once we get the root object, we can the get other data by relations
 *
 * @author stewart
 * @date 2023/6/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {

    /**
     * Parent Object
     */
    DataObject parent;

    /**
     * child Object
     */
    DataObject self;

    /**
     * child's uniqueId
     */
    String childId;

    /**
     * parent and child's relationShip
     * key is parent's column, value is children's column
     * once we get parent's data, we can get children by this relation.
     */
    Map<String, List<String>> relationShip;

    /**
     * children node
     * this is like a tree, we have a root node at DataRelations,and then we can find children by Relation
     */
    List<Relation> children;


}
