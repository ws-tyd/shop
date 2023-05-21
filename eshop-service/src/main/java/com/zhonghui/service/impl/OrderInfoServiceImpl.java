package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.*;
import com.zhonghui.model.*;
import com.zhonghui.resp.RespOrder;
import com.zhonghui.service.OrderInfoService;
import com.zhonghui.service.ProductService;
import com.zhonghui.service.UserAddressService;
import com.zhonghui.utils.SecuritUserUtil;
import com.zhonghui.utils.UserDetailUtil;
import com.zhonghui.vo.MyUserDetails;
import com.zhonghui.vo.PayOrder;
import com.zhonghui.vo.ReqOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private OrderStatusRecordMapper orderStatusRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public FwResult createOrder(ReqOrder reqOrder) {
        synchronized (this) {
            Product product = productMapper.selectById(reqOrder.getProductId());
            if (product == null) throw new BadRequestException(404, "商品不存在");
            else {
                UserAddress userAddress = userAddressMapper.selectById(reqOrder.getAddressId());
                if (userAddress == null) throw new BadRequestException(404, "地址不存在");
                else {
                    long time = new Date().getTime();
                    OrderInfo orderInfo = new OrderInfo(userAddress);
                    orderInfo.setPayment(product.getPrice());
                    orderInfo.setOrderNo(time + "0");
                    OrderStatusRecord orderStatusRecord = new OrderStatusRecord(0, LocalDateTime.now(), time + "0", time + "0", reqOrder.getProductId(), product.getName(), 0, "desc",1,product.getPrice());
                    orderStatusRecordMapper.insert(orderStatusRecord);
                    orderInfoMapper.insert(orderInfo);
                }
            }
        }
        return FwResult.ok("successful");
    }

    @Override
    public FwResult queryOrderInfoAll(PageUtil<OrderInfo> pageUtil) {
        MyUserDetails user = new SecuritUserUtil().getUser();
        IPage<RespOrder> orderInfoIPage;
        User one = user.getUser();
        if (one.getType().equals("user")) {
            orderInfoIPage = orderInfoMapper.queryPageByUserId(pageUtil, one.getId());
        } else {
            orderInfoIPage = orderInfoMapper.queryPage(pageUtil);
        }
        return FwResult.ok(orderInfoIPage, "successful");
    }

    @Override
    public FwResult queryOrderInfoByOrderNo(String orderNo) {
        MyUserDetails user = new SecuritUserUtil().getUser();
        RespOrder orderInfo;
        User one = user.getUser();
        if (one.getType().equals("user")) {
            orderInfo = orderInfoMapper.queryPageByOrderNoAndId(orderNo, one.getId());
        } else {
            orderInfo = orderInfoMapper.queryPageByOrderNo(orderNo);
        }
        return FwResult.ok(orderInfo, "successful");
    }

    @Override
    public FwResult deleteOrderById(Integer id) {
        User user = new SecuritUserUtil().getUser().getUser();
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", id);
        if (user.getType().equals("user")) {
            orderInfoQueryWrapper.eq("user_id", user.getId());
        }
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);
        if (orderInfo == null) {
            throw new BadRequestException(404, "不存在订单");
        }
        QueryWrapper<OrderStatusRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderInfo.getOrderNo());
        orderStatusRecordMapper.delete(wrapper);
        orderInfoMapper.deleteById(id);
        return FwResult.ok();
    }

    @Override
    public FwResult payOrderByOrderNo(PayOrder payOrder) {
        synchronized (this) {
            double total=0;
            User user = new SecuritUserUtil().getUser().getUser();
            User createrUser = userMapper.selectById(user.getId());
            QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
            orderInfoQueryWrapper.eq("order_no", payOrder.getOrderNo());
            OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);
            if (orderInfo == null) {
                throw new BadRequestException(404, "订单不存在");
            }
            orderInfo.setPaymentTime(LocalDateTime.now());
            orderInfo.setPaymentType(payOrder.getPayType());
            QueryWrapper<OrderStatusRecord> orderStatusRecordQueryWrapper = new QueryWrapper<>();
            orderStatusRecordQueryWrapper.eq("order_no", payOrder.getOrderNo());
            List<OrderStatusRecord> orderStatusRecords = orderStatusRecordMapper.selectList(orderStatusRecordQueryWrapper);
            for (OrderStatusRecord orderStatusRecord : orderStatusRecords) {
                orderStatusRecord.setStatus(1);
                total+=(orderStatusRecord.getProductPrice()*orderStatusRecord.getProductCount());
                orderStatusRecordMapper.updateById(orderStatusRecord);
            }
            if (total>createrUser.getBalance().doubleValue()){
                throw new BadRequestException(400,"余额不足");
            }
            orderInfo.setStatus(1);
            orderInfo.setPayment(total);
            orderInfoMapper.updateById(orderInfo);
            return FwResult.ok();
        }
    }
}

