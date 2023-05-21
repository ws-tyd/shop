package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.OrderInfo;
import com.zhonghui.service.OrderInfoService;
import com.zhonghui.vo.PayOrder;
import com.zhonghui.vo.ReqCartOrder;
import com.zhonghui.vo.ReqOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @PutMapping("/orders")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult createOrder(@Valid @RequestBody ReqOrder reqOrder){
        return orderInfoService.createOrder(reqOrder);
    }
    @PostMapping("/orders")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult queryOrderInfoAll(@Valid @RequestBody PageUtil<OrderInfo> pageUtil){
        return orderInfoService.queryOrderInfoAll(pageUtil);
    }

    @GetMapping("/orders/{orderNo}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult queryOrderInfoByOrderNo(@PathVariable("orderNo")String orderNo){
        return orderInfoService.queryOrderInfoByOrderNo(orderNo);
    }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult deleteOrderById(@PathVariable("id")Integer id){
        return orderInfoService.deleteOrderById(id);
    }
    @PostMapping("/orders/pay")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult payOrderByOrderNo(@Valid @RequestBody PayOrder payOrder){
        return orderInfoService.payOrderByOrderNo(payOrder);
    }
}
