package com.stewart.datatransport.pojo.persistent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author stewart
 * @since 2023-02-16
 */
@TableName("database_config")
@Builder
public class DatabaseConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * datasource name
     */
    private String name;

    /**
     * datasource uuid
     */
    private String databaseUniqueId;

    /**
     * datasource type : mysql, oracle etc...
     */
    private String databaseType;

    /**
     * instance for oracle database only
     */
    private String instance;

    /**
     * database username
     */
    private String username;

    /**
     * database password
     */
    private String password;

    /**
     * database name
     */
    private String databaseName;

    /**
     * json object of addresses
     */
    private String address;

    /**
     * datasource create time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabaseUniqueId() {
        return databaseUniqueId;
    }

    public void setDatabaseUniqueId(String databaseUniqueId) {
        this.databaseUniqueId = databaseUniqueId;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DatabaseConfig{" +
        "id=" + id +
        ", name=" + name +
        ", databaseUniqueId=" + databaseUniqueId +
        ", databaseType=" + databaseType +
        ", instance=" + instance +
        ", username=" + username +
        ", password=" + password +
        ", databaseName=" + databaseName +
        ", address=" + address +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
