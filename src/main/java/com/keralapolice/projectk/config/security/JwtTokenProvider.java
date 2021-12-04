package com.keralapolice.projectk.config.security;

import com.keralapolice.projectk.login.vo.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {


    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        String userId = "1";
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("username", "arun");
        claims.put("fullName", "arunprem");
        return Jwts.builder().setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid Signature");
            throw e;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid Token");
        } catch (ExpiredJwtException e) {
            System.out.println("Token Expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token not supported");
        } catch (IllegalArgumentException e) {
            System.out.println("Calims not valid");
        }
        return false;
    }

    public Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET_KEY).parseClaimsJws(token).getBody();
        int id = (int) claims.get("id");
        return Long.parseLong(String.valueOf(id));
    }


}
