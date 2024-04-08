package com.codigo.msticket.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    private String username=null;
    Claims claims=null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer ")){
            token = token.substring(7);
            username = jwtUtil.extracUserName(token);
            claims = jwtUtil.extractAllClaims(token);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            Claims claims = jwtUtil.extractAllClaims(token);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            String role = (String) claims.get("role");
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            UserDetails userDetails = new User(username, "", authorities);
            if(!jwtUtil.isTokenExpired(token)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            }
            else {
                response.setStatus(401);
            }
        }
    }
}
