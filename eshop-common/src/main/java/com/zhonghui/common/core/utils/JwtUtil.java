package com.zhonghui.common.core.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {

    private long time=24*60*60*1000*3;

    public String getToken(Map data){
        JwtBuilder builder = Jwts.builder();
        String token = builder.setHeaderParam("tyd", "jwt")
                .setHeaderParam("alg", "HS256")
                .claim("userName", data.get("userName"))
                .claim("password", data.get("password"))
                .claim("user",data.get("user"))
                .claim("role", data.get("role"))
                .setExpiration(new Date(new Date().getTime() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, "admin")
                .compact();

        return token;
    }

    public Map getBody(String token){
        JwtParser parser = Jwts.parser();
        Jwt parse = parser.setSigningKey("admin").parse(token);
        Object body = parse.getBody();
        Map map = (Map) body;
        return map;
    }
}
