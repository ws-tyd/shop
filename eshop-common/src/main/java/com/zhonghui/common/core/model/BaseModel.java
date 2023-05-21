package com.zhonghui.common.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description 基础实体类
 * @author zhonghui
 * @date 2019-9-20
 */
@Data
public class BaseModel {

    private static final long serialVersionUID = 42L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 创建人编码
     */
    @JsonIgnore
    @ApiModelProperty(value = "创建人id")
    private Integer createUser;
    /**
     * 修改人编码
     */
    @JsonIgnore
    @ApiModelProperty(value = "修改人id")
    private Integer updateUser;
    /**
     * 删除标记(1 删除 0未删除)
     */
    @JsonIgnore
    @ApiModelProperty(value = "删除标记(1 删除 0未删除)")
    @TableLogic
    private Integer deleteFlag=0;

}
