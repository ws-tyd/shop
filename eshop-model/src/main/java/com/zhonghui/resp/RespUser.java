package com.zhonghui.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhonghui.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespUser {
    private Integer  id;
    private String  login;
    private String  nickname;
    private String  type;
    private Double  balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  updated_at;

    public RespUser(User user) {
        this.id=user.getId();
        this.login=user.getUserName();
        this.nickname=user.getNickname();
        this.type=user.getType();
        this.balance=user.getBalance().doubleValue();
        this.created_at=user.getCreateTime();
        this.updated_at=user.getUpdateTime();
    }
}
