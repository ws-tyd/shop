package com.zhonghui.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhonghui.common.core.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class UpdateUser extends BaseModel {
    /**
     * 启用标志
     */
    @ApiModelProperty(value = "状态 1启用 0 禁用")
    @JsonIgnore
    private Integer status;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @Size(max = 11,message = "长度不能超出20位")
    private String userName;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @Size(max = 11,message = "长度不能超出11位")
    private String phone;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    private String  type;
    @Size(max = 11,message = "长度不能超出11位")
    private String nickname;
    @Range(min = 0,message = "余额数据范围错误")
    private BigDecimal balance;
}
