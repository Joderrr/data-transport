package com.stewart.datatransport.pojo.vo.database;

import com.stewart.datatransport.annotation.ParameterValid;
import com.stewart.datatransport.enums.ValidType;
import com.stewart.datatransport.enums.database.DatabaseType;
import com.stewart.datatransport.pojo.persistent.DatabaseConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * database config object, for save and edit database configuration
 *
 * @author stewart
 * @date 2023/1/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceConfig {

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
     * ip address
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private String address;

    /**
     * port
     */
    @ParameterValid(validType = ValidType.NOT_NULL)
    private String port;

    public DatabaseConfig toPersistent() {
        return DatabaseConfig.builder()
                .databaseUniqueId(this.databaseUniqueId)
                .databaseType(this.databaseType == null ? null : this.databaseType.getName())
                .instance(this.instance)
                .databaseName(this.databaseName)
                .name(this.name)
                .username(this.username)
                .password(this.password)
                .address(this.address)
                .port(this.port)
                .build();
    }

    public static DataSourceConfig readFromPersistent(DatabaseConfig databaseConfig){
        return DataSourceConfig.builder()
                .databaseUniqueId(databaseConfig.getDatabaseUniqueId())
                .databaseType(DatabaseType.getDatabaseTypeFromName(databaseConfig.getDatabaseType()))
                .instance(databaseConfig.getInstance())
                .databaseName(databaseConfig.getDatabaseName())
                .name(databaseConfig.getName())
                .username(databaseConfig.getUsername())
                .password(databaseConfig.getPassword())
                .address(databaseConfig.getAddress())
                .port(databaseConfig.getPort())
                .build();
    }

}
