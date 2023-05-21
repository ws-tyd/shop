package com.zhonghui.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhonghui.common.core.result.FwResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

      log.error(request.getRequestURL().toString()+"===>"+e.getMessage());
        System.out.println(e.getMessage());
        Integer code = (Integer) request.getAttribute("errorCode");
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (code==null){
            code=401;
        }
        if (errorMsg==null){
            errorMsg="认证失败，请重新登录后重试";
        }
        response.setStatus(code);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(FwResult.failedMsg(code,errorMsg)));
    }
}
