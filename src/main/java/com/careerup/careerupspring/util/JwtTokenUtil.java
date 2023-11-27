package com.careerup.careerupspring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    public static String createToken(String email, String roleType, long expireTimeMs, String key){
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

    private static Claims getAllClaims(String token, String key) {
        return Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody();
    }

    public static String getSeekerEmail(String token, String key) {
        if (!isTokenExpired(token, key)) {
            String seekerEmail = String.valueOf(getAllClaims(token, key).get("email"));
            return seekerEmail;
        } else {
            return "cannot find seeker";
        }
    }

    public static Date getExperationDate(String token, String key) {
        Claims claims = getAllClaims(token, key);
        return claims.getExpiration();
    }

    private static boolean isTokenExpired(String token, String key) {
        return getExperationDate(token, key).before(new Date());
    }
}
