package com.zhonghui.filter;

import com.zhonghui.common.core.config.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RequserCountFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        synchronized (this){
            String key=request.getRemoteAddr()+request.getRequestURI()+request.getMethod();
            if (redisUtils.hasKey(key)){
                Integer num = getNub(redisUtils.get(key));
                if (num>100){
                    request.setAttribute("errorCode",429);
                    request.setAttribute("errorMsg","请求次数太多了");
                    throw new RuntimeException("请求次数过多");
                }else {
                    num++;
                    redisUtils.set(key,num.toString(),1, TimeUnit.MINUTES);
                }
            }else {
                redisUtils.set(key,"1",1, TimeUnit.MINUTES);
            }
            filterChain.doFilter(request,response);
        }
    }

    public Integer getNub(String s){
        int num=0;
        for (int i = 0; i <s.length(); i++) {
            num=num*10+(s.charAt(i)-'0');
        }
        return num;
    }
}
