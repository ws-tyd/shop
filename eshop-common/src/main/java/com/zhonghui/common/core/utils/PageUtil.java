package com.zhonghui.common.core.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtil<T> implements IPage<T> {

    private List<T> records;
    private long total;
    @Range(min = 1,message = "页面最小为1")
    private long current=1;
    @Range(min = 1,message = "页面最小为1")
    private long size=10;
    @JsonIgnore
    private List<OrderItem> orders;
    @JsonIgnore
    private boolean optimizeCountSql;
    @JsonIgnore
    private boolean isSearchCount=true;

    @Override
    public List<OrderItem> orders() {
        return this.orders;
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records=records;
        return  this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total=total;
        return this;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size=size;
        return this;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current=current;
        return this;
    }
}

/****
 *
 *
 * @Data
 * @AllArgsConstructor
 * @NoArgsConstructor
 * public class PageUtil<T> extends IPage<T> {
 *     @NotNull(message = "页面不能为空")
 *     @Range(min = 1,message = "页面最小为1")
 *     Integer current=1;
 *     @NotNull(message = "页面大小不能为空")
 *     @Range(min = 1,message = "页面最小为1")
 *     Integer size=10;
 *
 *     public Page<T> getPage(){
 *         return new Page<T>(this.current,this.size);
 *     }
 * }
 */