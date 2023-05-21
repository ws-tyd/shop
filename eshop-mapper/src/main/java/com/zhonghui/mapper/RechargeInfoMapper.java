package com.zhonghui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhonghui.model.RechargeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RechargeInfoMapper extends BaseMapper<RechargeInfo> {


}
