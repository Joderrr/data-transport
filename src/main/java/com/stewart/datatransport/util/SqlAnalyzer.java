package com.stewart.datatransport.util;

import com.stewart.datatransport.enums.object.DataType;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql analyzerr
 *
 * @author stewart
 * @date 2023/12/5
 */
public class SqlAnalyzer {

    /**
     * to get tablename from sql script
     * @param sql     sql script
     * @return        tablename or emptyStr
     */
    public static String extractSingleTableName(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(new StringReader(sql));
            if (statement instanceof Select) {
                Select select = (Select) statement;
                PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
                return plainSelect.getFromItem().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * to get query condition from sql script
     * @param sql     sql script
     * @return        tablename or emptyStr
     */
    public static List<String> extractFieldsFromWhereClause(String sql) {
        List<String> fieldsList = new ArrayList<>();
        try {
            Statement statement = CCJSqlParserUtil.parse(new StringReader(sql));
            if (statement instanceof Select) {
                Select select = (Select) statement;
                PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
                ExpressionDeParser expressionDeParser = new ExpressionDeParser();
                plainSelect.getWhere().accept(expressionDeParser);
                String whereClause = expressionDeParser.getBuffer().toString();
                String[] conditions = whereClause.split("\\s*(=|>|<|>=|<=|!=|\\band\\b|\\bor\\b)\\s*");
                for (String condition : conditions) {
                    condition = condition.trim();
                    if (!condition.isEmpty() && !condition.matches("[0-9]+")) {
                        fieldsList.add(condition);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldsList;
    }

    /**
     * convert sql to placeholder sql
     * @param originalSql   originalsql string
     * @return    replaced sql
     */
    public static String convertToPlaceholders(String originalSql) {
        // 匹配字段名和对应的值
        String regex = "(\\w+)\\s*[=<>]+\\s*['\"]([^'\"]+)['\"]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(originalSql);

        // 逐个替换
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String fieldName = matcher.group(1);
            String value = matcher.group(2);

            // 替换为占位符形式
            matcher.appendReplacement(result, fieldName + "=#{" + fieldName + "}");
        }
        matcher.appendTail(result);

        return result.toString();
    }


    public static Map<String, DataType> extractFields(String sql) {
        Map<String, DataType> fieldMap = new HashMap<>();
        String expression = sql.split(" from ")[0].replace("select", "");
        String[] columns = expression.split(",");
        for (String column : columns) {
            fieldMap.put(column.replace(" ",""), DataType.String);
        }
        return fieldMap;
    }

}
