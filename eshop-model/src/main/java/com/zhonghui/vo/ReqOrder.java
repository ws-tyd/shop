package com.zhonghui.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqOrder {
    @NotNull(message = "商品信息不能为空")
    @Range(min = 1,message = "商品id最小从一开始")
    private Integer productId;
    @NotNull(message = "地址信息不能为空")
    @Range(min = 1,message = "地址id最小从一开始")
    private Integer addressId;
}
