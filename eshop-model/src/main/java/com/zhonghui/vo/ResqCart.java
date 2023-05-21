package com.zhonghui.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class ResqCart {

    @NotNull(message = "商品不能为空")
    @Range(min = 1,message = "商品范围有误")
    private Integer proId;
    @NotNull(message = "商品数量不能为空")
    @Range(min = 1,message = "商品数量范围有误")
    private Integer proCount;

}
