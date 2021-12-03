package com.keralapolice.projectk.config.security;

import com.keralapolice.projectk.login.vo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    public String generateToken(){
        //User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime()+SecurityConstants.EXPIRATION_TIME);
        String userId ="1";
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1L);
        claims.put("username","arun");
        claims.put("fullName","arunprem");
        return Jwts.builder().setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET_KEY)
                .compact();
    }


}
