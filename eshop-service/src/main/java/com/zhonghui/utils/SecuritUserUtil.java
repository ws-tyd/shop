package com.zhonghui.utils;

import com.zhonghui.vo.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecuritUserUtil {

    public MyUserDetails getUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) {
           return (MyUserDetails) principal;
        }
        return null;
    }
}
