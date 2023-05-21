package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.OrderInfo;
import com.zhonghui.vo.PayOrder;
import com.zhonghui.vo.ReqOrder;

public interface OrderInfoService extends IService<OrderInfo> {
    FwResult createOrder(ReqOrder reqOrder);

    FwResult queryOrderInfoAll(PageUtil<OrderInfo> pageUtil);

    FwResult queryOrderInfoByOrderNo(String orderNo);

    FwResult deleteOrderById(Integer id);

    FwResult payOrderByOrderNo(PayOrder payOrder);
}
