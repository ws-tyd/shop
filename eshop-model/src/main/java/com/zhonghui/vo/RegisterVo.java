package com.zhonghui.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterVo {
    @NotNull(message = "账号不能为空")
    private String login;
    @NotNull(message = "网名不能为空")
    private String nickname;
    @NotNull(message = "密码不能为空")
    private String password;
}
