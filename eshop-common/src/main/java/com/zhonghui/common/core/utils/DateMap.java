package com.zhonghui.common.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class DateMap {
    private Map<String,Object> data;

    public DateMap() {
        this.data=new HashMap<>();
    }
    public DateMap put(String key,Object val){
        this.data.put(key,val);
        return this;
    }

}
