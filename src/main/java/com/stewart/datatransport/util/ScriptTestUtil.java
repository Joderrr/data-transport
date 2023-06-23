package com.stewart.datatransport.util;

import com.alibaba.fastjson2.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stewart
 * @date 2023/2/17
 */
public class ScriptTestUtil {
    public static void main(String[] args) {
        String queryScript = "select   a.abc    hhh    ,  a.bcd jjj,    b.abc      kkk from a,b where 1 = 1";
        resolveQueryColumn(queryScript);
    }

    private static void resolveQueryColumn(String queryScript){
        List<Map<String,String>> columnList = new ArrayList();
        int selectIndex = queryScript.indexOf("select");
        int fromIndex = queryScript.indexOf("from");
        String columnsStr = queryScript.substring(selectIndex + 6, fromIndex);

        String[] splitColumn = columnsStr.split(",");
        for (String column : splitColumn) {
            Map<String,String> columnMap = new HashMap<>();
            while (column.startsWith(" ")){
                column = column.replaceFirst(" ", "");
            }
            if(column.contains(" ")){
                String[] columnAndAlias = column.split(" ");
                columnMap.put(columnAndAlias[0],columnAndAlias[1]);
            } else {
                columnMap.put(column, column);
            }
            columnList.add(columnMap);
        }
        System.out.println(JSON.toJSONString(columnList));
        //return columnList;
    }
}
