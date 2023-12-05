package com.careerup.careerupspring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    public static String key;

    @Value("${jwt.token.key}")
    public void setKey(String key){
        this.key = key;
    }

    // token 생성
    public static String createToken(String email, String roleType, long expireTimeMs){
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

    // token parsing
    private static Claims getAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception e){
            throw new IllegalStateException("권한이 없습니다.");
        }

    }

    // token에서 email 추출
    public static String getUserEmail(String token) {
        if (!isTokenExpired(token)) {
            String userEmail = String.valueOf(getAllClaims(token).get("email"));
            return userEmail;
        } else throw new IllegalStateException("토큰이 유효하지 않습니다.");
    }

    // token에서 roleType 추출
    public static String getUserRoleType(String token) {
        if (!isTokenExpired(token)) {
            String roleType = String.valueOf(getAllClaims(token).get("roleType"));
            return roleType;
        } else throw new IllegalStateException("토큰이 유효하지 않습니다.");
    }

    // token 유효성 검증
    public static Date getExperationDate(String token) {
        Claims claims = getAllClaims(token);
        return claims.getExpiration();
    }

    private static boolean isTokenExpired(String token) {
        return getExperationDate(token).before(new Date());
    }
}
