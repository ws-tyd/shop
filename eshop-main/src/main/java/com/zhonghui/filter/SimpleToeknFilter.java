package com.zhonghui.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.zhonghui.common.core.config.RedisUtils;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.utils.JwtUtil;
import com.zhonghui.model.User;
import com.zhonghui.service.UserService;
import com.zhonghui.utils.UserDetailUtil;
import com.zhonghui.vo.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class SimpleToeknFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public static final String USER_LOGIN_TOKEN = "user_login_token_";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)&&authorization.length()>10){
            authorization=authorization.substring("Bearer ".length());
            if (redisUtils.hasKey(authorization)) {
            try {
                Map body = jwtUtil.getBody(authorization);
                UserDetailUtil userDetailUtil = new UserDetailUtil(userService);
                Authentication authentication = userDetailUtil.getUser(body.get("userName").toString());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                request.setAttribute("errorCode",401);
                request.setAttribute("errorMsg","认证失败,tonken失效");
                throw new BadRequestException(401,"认证失败");
            }
            }else {
                System.out.println(authorization);
                request.setAttribute("errorCode",401);
                request.setAttribute("errorMsg","认证失败,tonken失效");
                throw new BadRequestException(401,"认证失败");
            }
        }
        filterChain.doFilter(request,response);
    }
}
