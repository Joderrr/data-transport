package com.stewart.datatransport.pojo.vo.database;

import com.stewart.datatransport.annotation.ParameterValid;
import com.stewart.datatransport.enums.ValidType;
import com.stewart.datatransport.enums.database.DatabaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * database config object, for save and edit database configuration
 *
 * @author stewart
 * @date 2023/1/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseConfig {

    /**
     * database config name
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private String name;

    /**
     * unique id, generally use uuid
     */
    private String databaseUniqueId;

    /**
     * database type
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private DatabaseType databaseType;

    /**
     * database instance for oracle use only
     */
    private String instance;

    /**
     * username
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private String username;

    /**
     * password
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private String password;

    /**
     * database name
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private String databaseName;

    /**
     * address and port information
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private List<AddressAndPort> address;

}
