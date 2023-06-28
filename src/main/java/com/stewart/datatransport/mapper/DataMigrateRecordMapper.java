package com.stewart.datatransport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stewart.datatransport.pojo.persistent.DataMigrateRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stewart
 * @since 2023-02-16
 */
@Mapper
public interface DataMigrateRecordMapper extends BaseMapper<DataMigrateRecord> {

}
