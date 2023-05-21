package com.zhonghui.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReqCartOrder {
    @NotNull(message = "商品信息不能为空")
    private List<CartPro> cartPros;
    @NotNull(message = "地址信息不能为空")
    private Integer addressId;
}
