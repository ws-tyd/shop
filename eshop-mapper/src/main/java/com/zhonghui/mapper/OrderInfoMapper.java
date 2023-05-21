package com.zhonghui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.OrderInfo;
import com.zhonghui.model.Product;
import com.zhonghui.resp.RespOrder;
import com.zhonghui.vo.ReqOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    @Select("select *,order_no as orderNo from order_info  where user_id=#{id}")
    @Results({
            @Result(column = "order_no",property = "details",one = @One(select = "com.zhonghui.mapper.OrderInfoMapper.queryByOrderNo"))
    })
    IPage<RespOrder> queryPageByUserId(PageUtil<OrderInfo> pageUtil, @Param("id") Integer id);

    @Select("select *,order_no as orderNo from order_info  where  order_no=#{orderNo}")
    @Results({
            @Result(column = "order_no",property = "details",one = @One(select = "com.zhonghui.mapper.OrderInfoMapper.queryByOrderNo"))
    })
    RespOrder queryPageByOrderNo(@Param("orderNo") String orderNo);

    @Select("select * ,order_no as orderNo from order_info")
    @Results({
            @Result(column = "order_no",property = "details",one = @One(select = "com.zhonghui.mapper.OrderInfoMapper.queryByOrderNo"))
    })
    IPage<RespOrder> queryPage(PageUtil<OrderInfo> pageUtil);

    @Select("select * from  product a left join order_status_record b on b.product_id=a.id where order_no=#{orderNo}")
    List<Product> queryByOrderNo(@Param("orderNo") String orderNo);

    @Select("select *,order_no as orderNo from order_info  where user_id=#{id} and order_no=#{orderNo}")
    @Results({
            @Result(column = "order_no",property = "details",one = @One(select = "com.zhonghui.mapper.OrderInfoMapper.queryByOrderNo"))
    })
    RespOrder queryPageByOrderNoAndId(@Param("orderNo") String orderNo, @Param("id") Integer id);
}
