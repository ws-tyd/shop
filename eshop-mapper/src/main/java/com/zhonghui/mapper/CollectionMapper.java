package com.zhonghui.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionMapper extends BaseMapper<Collection> {


//    @Select("select * from collection limit #{skip},#{take}")
//    List<Collection> selectByPage(PageUtil<Collection> skip, QueryWrapper<Collection> take);
}
