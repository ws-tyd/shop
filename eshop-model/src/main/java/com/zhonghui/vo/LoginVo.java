package com.zhonghui.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhonghui
 * @description 登录试图
 * @date 2020/12/4
 */
@Data
public class LoginVo {


    /**
     * 手机号
     */
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String login;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getUserName(){
        return this.login;
    }

}
