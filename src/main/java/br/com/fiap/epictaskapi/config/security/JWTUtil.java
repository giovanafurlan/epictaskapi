package br.com.fiap.epictaskapi.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String generateToken(String email) {
        Long expirationLong = Long.parseLong(expiration);
        Date dateExpiration = new Date();
        dateExpiration.setTime(System.currentTimeMillis() + expirationLong);
        return Jwts.builder().setSubject(email).
                setExpiration(dateExpiration).
                signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            return username;
        }
        return null;
    }
}