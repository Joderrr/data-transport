package com.stewart.datatransport.service;

import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.database.DataSourceConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Data Migration Service
 *
 * @author stewart
 * @date 2023/6/11
 */
public interface MigrationService {

    /**
     * generate migrate data package by using DataSetConfig and its condition
     *
     * as we know, at this program, dataset contains a root and it's children, we manage data relations as a tree.
     * at this method, we get all data from root with it's condition.
     *
     * @param dataSetUniqueId   dataset unique id
     * @param condition  query condition
     * @param response   response, use io stream to out put the file
     */
    void generateMigratePackage(String dataSetUniqueId, Map<String, String> condition, HttpServletResponse response);

    /**
     * import mogration data to the given configuration
     *
     * @param dataSourceConfig  datasource configuration
     * @param file              import data file
     * @return                  import status
     */
    GeneralResponse importMigrationPackage(DataSourceConfig dataSourceConfig, MultipartFile file);
}
