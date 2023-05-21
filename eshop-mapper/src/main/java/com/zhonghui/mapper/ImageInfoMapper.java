package com.zhonghui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhonghui.model.ImageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImageInfoMapper extends BaseMapper<ImageInfo> {
//    @Select("select * from image_info limit #{skip},#{take}")
//    List<ImageInfo> selectPage(int skip, int take);
}
