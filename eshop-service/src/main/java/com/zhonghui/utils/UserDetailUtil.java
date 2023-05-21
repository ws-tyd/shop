package com.zhonghui.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhonghui.model.User;
import com.zhonghui.service.UserService;
import com.zhonghui.vo.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserDetailUtil {

    private final UserService userService;
    @Autowired
    public UserDetailUtil(UserService userService) {
        this.userService = userService;
    }

    public Authentication getUser(String userName){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        User user = userService.getOne(wrapper);
        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setUser(user);
        userDetails.setUsername(userName);
        userDetails.setPassword(user.getPassword());
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getType()));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities);
        return token;
    }
}
