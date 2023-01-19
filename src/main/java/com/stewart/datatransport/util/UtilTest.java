package com.stewart.datatransport.util;

import org.springframework.util.CollectionUtils;

import javax.naming.InitialContext;
import java.util.Arrays;
import java.util.Map;

/**
 * @author stewart
 * @date 2023/1/19
 */
public class UtilTest {

    public static void main(String[] args) {
        String name = "tableId";
        System.out.println(initialCapitalize(name));
    }

    private static String initialCapitalize(String fieldName){
        char[] chars = fieldName.toCharArray();
        chars[0] = String.valueOf(chars[0]).toUpperCase().toCharArray()[0];
        return String.valueOf(chars);
    }

}
