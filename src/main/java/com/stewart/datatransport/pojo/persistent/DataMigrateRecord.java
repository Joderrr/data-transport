package com.stewart.datatransport.pojo.persistent;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("data_migrate_record")
public class DataMigrateRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     *  data migrate record uuid
     */
    private String migrateUniqueId;

    /**
     * data migrate object sets
     */
    private String migrateSets;

    /**
     * data migration type: import, export
     */
    private String migrateType;

    /**
     * data migration file name
     */
    private String fileName;

    /**
     * data migration file saved url
     */
    private String fileUrl;

    /**
     * data migrate time
     */
    private LocalDateTime createTime;

    /**
     * data migrate time
     */
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMigrateUniqueId() {
        return migrateUniqueId;
    }

    public void setMigrateUniqueId(String migrateUniqueId) {
        this.migrateUniqueId = migrateUniqueId;
    }

    public String getMigrateSets() {
        return migrateSets;
    }

    public void setMigrateSets(String migrateSets) {
        this.migrateSets = migrateSets;
    }

    public String getMigrateType() {
        return migrateType;
    }

    public void setMigrateType(String migrateType) {
        this.migrateType = migrateType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
        return "DataMigrateRecord{" +
        "id=" + id +
        ", migrateUniqueId=" + migrateUniqueId +
        ", migrateSets=" + migrateSets +
        ", migrateType=" + migrateType +
        ", fileName=" + fileName +
        ", fileUrl=" + fileUrl +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
