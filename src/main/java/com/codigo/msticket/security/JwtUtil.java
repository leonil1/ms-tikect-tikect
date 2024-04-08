package com.codigo.msticket.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String secret = "codigoJWT";

    public String extracUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        try{
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);
    }

    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7200000)) // 2 horas = 7200000
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails ){

        final String username = extracUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
