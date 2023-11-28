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


    public static String createToken(String email, String roleType, long expireTimeMs){
        System.out.println(key);
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

    private static Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody();
    }

    public static String getSeekerEmail(String token) {
        if (!isTokenExpired(token)) {
            String seekerEmail = String.valueOf(getAllClaims(token).get("email"));
            return seekerEmail;
        } else {
            return "cannot find seeker";
        }
    }

    public static Date getExperationDate(String token) {
        Claims claims = getAllClaims(token);
        return claims.getExpiration();
    }

    private static boolean isTokenExpired(String token) {
        return getExperationDate(token).before(new Date());
    }
}
