package com.zhonghui.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class PayOrder {
    @NotNull(message = "付款方式不能为空")
    @Range(min = 0,max = 20,message = "付款方式错误")
    private Integer payType;
    @NotNull(message = "订单号不能为空")
    private String orderNo;
}
