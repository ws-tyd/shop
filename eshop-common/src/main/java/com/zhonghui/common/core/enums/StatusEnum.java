package com.zhonghui.common.core.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @description 启用禁用枚举
 * @author zhonghui
 * @date 2019/2/1
 */
public enum StatusEnum implements IEnum<Integer> {

    DISABLED(0, "禁用"),
    ENABLE(1, "启用");

    private Integer value;
    private String desc;

    StatusEnum(int value, String desc) {
        this.value=value;
        this.desc=desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }
}
