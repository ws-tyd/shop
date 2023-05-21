package com.zhonghui.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class ReqCart {
    @NotNull(message = "选择状态必须存在")
    private Boolean selected;
    @NotNull(message = "数量必须存在")
    @Range(min = 1,message = "商品数量最小为一")
    private Integer quantity;
}
