package com.stewart.datatransport.util;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

}
