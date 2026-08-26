package com.fitnessapp.filter;

import com.fitnessapp.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;


    @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {

    System.out.println("🔥🔥🔥 JWT FILTER SE IZVRŠAVA ZA: " + request.getRequestURI());

    String authHeader = request.getHeader("Authorization");
    System.out.println("🔐 Auth header: " + authHeader);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        System.out.println("❌ Nema tokena ili nije Bearer");
        filterChain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7);
    System.out.println("🔑 Token: " + token.substring(0, Math.min(token.length(), 30)) + "...");

    boolean isValid = jwtService.isTokenValid(token);
    System.out.println("🔍 Da li je token validan? " + isValid);

    if (isValid) {
        String email = jwtService.extractEmail(token);
        System.out.println("✅ Token validan! Email: " + email);

        List<SimpleGrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_USER")
        );
        System.out.println("🔐 Authorities: " + authorities);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(email, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("✅ Autentifikacija postavljena");

        // Provera da li je stvarno postavljena
        System.out.println("📌 SecurityContext sada ima: " + SecurityContextHolder.getContext().getAuthentication());
    } else {
        System.out.println("❌ Token NIJE validan!");
    }

    System.out.println("🔐 Pre filterChain, SecurityContext ima: " + SecurityContextHolder.getContext().getAuthentication());
    filterChain.doFilter(request, response);
}




    /* 

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (jwtService.isTokenValid(token)) {
            String email = jwtService.extractEmail(token);

            List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER")
            );

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
    */
    
}