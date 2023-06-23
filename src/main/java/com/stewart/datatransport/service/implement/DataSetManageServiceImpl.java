package com.stewart.datatransport.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stewart.datatransport.mapper.DataSetMapper;
import com.stewart.datatransport.pojo.persistent.DataSet;
import com.stewart.datatransport.pojo.vo.base.GeneralResponse;
import com.stewart.datatransport.pojo.vo.dataset.DataSetConfig;
import com.stewart.datatransport.service.BaseService;
import com.stewart.datatransport.service.DataSetManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Data set management service
 *
 * @author stewart
 * @date 2023/6/11
 */
@Service
public class DataSetManageServiceImpl extends BaseService implements DataSetManageService {

    @Resource
    DataSetMapper dataSetMapper;

    /**
     * @see DataSetManageService#saveDataSet(DataSetConfig)
     */
    @Override
    public GeneralResponse saveDataSet(DataSetConfig dataSetConfig) {
        int insert = dataSetMapper.insert(dataSetConfig.toPersistent());
        return generateResponseObject(insert > 0, "data set insert success", "data set insert failure");
    }

    /**
     * @see DataSetManageService#deleteDataSet(String)
     */
    @Override
    public GeneralResponse deleteDataSet(String dataSetUniqueId) {
        int delete = dataSetMapper.delete(new LambdaQueryWrapper<DataSet>()
                .eq(DataSet::getDataSetUniqueId, dataSetUniqueId));
        return generateResponseObject(delete > 0, "data set delete success", "data set delete failure");
    }

    /**
     * @see DataSetManageService#updateDataSet(DataSetConfig)
     */
    @Override
    public GeneralResponse updateDataSet(DataSetConfig dataSetConfig) {
        int update = dataSetMapper.update(dataSetConfig.toPersistent(), new LambdaQueryWrapper<DataSet>()
                .eq(DataSet::getDataSetUniqueId, dataSetConfig.getDataSetUniqueId()));
        return generateResponseObject(update > 0, "data set update success", "data set update failure");
    }

    /**
     * @see DataSetManageService#queryDataSet(DataSetConfig)
     */
    @Override
    public GeneralResponse queryDataSet(DataSetConfig dataSetConfig) {
        LambdaQueryWrapper<DataSet> wrapper = new LambdaQueryWrapper<>();
        if(dataSetConfig.getName() != null){
            wrapper.like(DataSet::getName, dataSetConfig.getName());
        }
        if(dataSetConfig.getDataSetUniqueId() != null){
            wrapper.eq(DataSet::getDataSetUniqueId, dataSetConfig.getDataSetUniqueId());
        }
        return generateSuccessfulResponseObject(dataSetMapper.selectList(wrapper));
    }


}
