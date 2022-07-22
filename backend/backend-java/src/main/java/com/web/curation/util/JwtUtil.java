package com.web.curation.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private static final String SALT = "SSAFIT";
    private static final long EXP_TIME = 1000L * 3000;

    public String createToken(String claimId, String data) throws Exception {
        return Jwts.builder() 
                .setHeaderParam("alg", "HS256") 
                .setHeaderParam("typ", "JWT") 
                .claim(claimId, data) 
                .setExpiration( 
                        new Date( 
                                System.currentTimeMillis() + EXP_TIME 
                        ) 
                )
                .signWith(SignatureAlgorithm.HS256, SALT.getBytes("UTF-8")) 
                .compact();
    }

    public void valid(String token) throws Exception {
        Jwts.parser() 
                .setSigningKey(SALT.getBytes("UTF-8")) 
                .parseClaimsJws(token);
    }

    public String getTokenId(String token) throws Exception {
        return Jwts.parser() 
                .setSigningKey(SALT.getBytes("UTF-8")) 
                .parseClaimsJws(token) 
                .getBody().get("userId").toString();
    }
}
