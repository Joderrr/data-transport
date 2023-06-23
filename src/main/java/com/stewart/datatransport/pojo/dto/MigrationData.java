package com.stewart.datatransport.pojo.dto;

import com.stewart.datatransport.pojo.vo.dataset.DataSetConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Migration Data
 *
 * contains migration's configuration and it's data, data structure etc.
 *
 * @author stewart
 * @date 2023/6/24
 */
@Data
@AllArgsConstructor
public class MigrationData {

    DataSetConfig dataSetConfig;

    Map<String, List<Map<String,String>>> migrationData;

}
