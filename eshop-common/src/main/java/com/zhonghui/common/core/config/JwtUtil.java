package com.zhonghui.common.core.config;

import cn.hutool.crypto.SecureUtil;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    private static long time=24*60*60*1000;


    public static String getToken(Map data,int day){
        long expTime=time*day;
        JwtBuilder builder = Jwts.builder();
        String token = builder.setHeaderParam("tyd", "jwt")
                .setHeaderParam("alg", "HS256")
                .claim("userName", data.get("userName"))
                .claim("password", data.get("password"))
                .claim("role", data.get("role"))
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(new Date().getTime() + expTime))
                .signWith(SignatureAlgorithm.HS256, "admin")
                .compact();
        return token;
    }

    public static Map getbody(String jwt){
        JwtParser parser = Jwts.parser();
        Jwt data = parser.setSigningKey("admin").parse(jwt);
        Map body = (Map) data.getBody();
        return body;
    }
}
