package com.zhonghui.config;

import com.zhonghui.filter.RequserCountFilter;
import com.zhonghui.filter.SimpleAccessDeniedHandler;
import com.zhonghui.filter.SimpleAuthenticationEntryPoint;
import com.zhonghui.filter.SimpleToeknFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {


    @Autowired
    private SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;
    @Autowired
    private SimpleAccessDeniedHandler simpleAccessDeniedHandler;

    @Autowired
    private SimpleToeknFilter simpleToeknFilter;

    @Autowired
    private RequserCountFilter requserCountFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/login","/logout","/file/pic/**","/product/**","/getTokenUser","/register","/collection/**"
                        ,"/banner","/category","/product/getHot","/product","/image","/image/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        http.logout().disable();
        http.exceptionHandling()
                .authenticationEntryPoint(simpleAuthenticationEntryPoint)
                .accessDeniedHandler(simpleAccessDeniedHandler);
        http.addFilterBefore(simpleToeknFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(requserCountFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
