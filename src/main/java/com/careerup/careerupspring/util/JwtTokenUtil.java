package com.careerup.careerupspring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String createToken(String email, String nickname, String key){
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("nickname", nickname);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS384, key)
                .compact();
    }
}
