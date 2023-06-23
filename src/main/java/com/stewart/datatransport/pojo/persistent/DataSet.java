package com.stewart.datatransport.pojo.persistent;

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
@TableName("data_set")
@Builder
public class DataSet implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * data set name
     */
    private String name;

    /**
     * data set uuid
     */
    private String dataSetUniqueId;

    /**
     * data objects that data set includes
     */
    private String dataObjects;

    /**
     * data set configuration metadata
     */
    private String metadata;

    /**
     * data set create time
     */
    private LocalDateTime createTime;

    /**
     * data set lasted update time
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

    public String getDataSetUniqueId() {
        return dataSetUniqueId;
    }

    public void setDataSetUniqueId(String dataSetUniqueId) {
        this.dataSetUniqueId = dataSetUniqueId;
    }

    public String getDataObjects() {
        return dataObjects;
    }

    public void setDataObjects(String dataObjects) {
        this.dataObjects = dataObjects;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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
        return "DataSet{" +
        "id=" + id +
        ", name=" + name +
        ", dataSetUniqueId=" + dataSetUniqueId +
        ", dataObjects=" + dataObjects +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
