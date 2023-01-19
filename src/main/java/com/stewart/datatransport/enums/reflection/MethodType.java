package com.stewart.datatransport.enums.reflection;

import lombok.Data;
import lombok.Getter;

/**
 * getter/setter method enum with its prefix
 *
 * @author stewart
 * @date 2023/1/19
 */
@Getter
public enum MethodType {

    /**
     * getter method enum
     */
    GETTER("get"),

    /**
     * setter method enum
     */
    SETTER("set");

    /**
     * constructor method
     *
     * @param prefix    method prefix
     */
    MethodType(String prefix){
        this.prefix = prefix;
    }

    /**
     * prefix of getter/setter method name
     */
    final String prefix;
}
