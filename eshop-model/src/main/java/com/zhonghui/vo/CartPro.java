package com.zhonghui.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class CartPro {

    @NotNull(message = "商品ID不能为空")
    @Range(min = 1,message = "商品ID必须大于等于1")
    private Integer productId;
    @NotNull(message = "商品数量不能为空")
    @Range(min = 1,message = "商品数量必须大于等于1")
    private Integer count;
}
