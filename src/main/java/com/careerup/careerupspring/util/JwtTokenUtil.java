package com.careerup.careerupspring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String createToken(String email, String roleType, long expireTimeMs,String key){
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("roleType", roleType);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expireTimeMs))
                .signWith(SignatureAlgorithm.HS384, key)
                .compact();
    }
}
