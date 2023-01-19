package com.stewart.datatransport.enums.database;

import lombok.Getter;

/**
 * database type enum
 *
 * @author stewart
 * @date 2023/1/17
 */
@Getter
public enum DatabaseType {

    /**
     * mysql database enum
     */
    MySQL("mysql"),

    /**
     * oracle database enum
     */
    Oracle("oracle"),

    /**
     * didn't match
     */
    None("none");

    /**
     * enum name
     */
    final String name;

    /**
     * constructor method
     *
     * @param name  enum's name field
     */
    DatabaseType(String name){
        this.name = name;
    }

    /**
     * by using enum's name to find enum instance
     *
     * @param name enum's name field
     * @return  enum instance
     */
    public static DatabaseType getDatabaseTypeFromName(String name){
        for (DatabaseType value : DatabaseType.values()) {
            if(value.name.equals(name)){
                return value;
            }
        }
        return None;
    }
}
