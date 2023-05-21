package com.zhonghui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhonghui.model.Category;
import com.zhonghui.resp.RespCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select * from category")
    List<RespCategory> queryAllByTree();
}
