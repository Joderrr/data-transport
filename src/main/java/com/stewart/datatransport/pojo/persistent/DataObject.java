package com.stewart.datatransport.pojo.persistent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@TableName("data_object")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * data object name
     */
    private String name;

    /**
     * data object related datasource
     */
    private String datasourceId;

    /**
     * data object uuid
     */
    private String dataObjectUniqueId;

    /**
     * data object table name
     */
    private String tableName;

    /**
     * data object query condition
     */
    private String queryCondition;

    /**
     * data object query script
     */
    private String queryScript;

    /**
     * data object structure
     */
    private String dataStructure;

    /**
     * data object create time
     */
    private LocalDateTime createTime;

    /**
     * data object update time
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

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDataObjectUniqueId() {
        return dataObjectUniqueId;
    }

    public void setDataObjectUniqueId(String dataObjectUniqueId) {
        this.dataObjectUniqueId = dataObjectUniqueId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getQueryScript() {
        return queryScript;
    }

    public void setQueryScript(String queryScript) {
        this.queryScript = queryScript;
    }

    public String getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(String dataStructure) {
        this.dataStructure = dataStructure;
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
        return "DataObject{" +
        "id=" + id +
        ", name=" + name +
        ", datasourceId=" + datasourceId +
        ", dataObjectUniqueId=" + dataObjectUniqueId +
        ", queryScript=" + queryScript +
        ", dataStructure=" + dataStructure +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
