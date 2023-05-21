package com.zhonghui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhonghui.model.Product;
import com.zhonghui.resp.RespPro;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper extends BaseMapper<Product> {

//    List<RespPro> queryHotPro(@Param("pageUtil") )
}
