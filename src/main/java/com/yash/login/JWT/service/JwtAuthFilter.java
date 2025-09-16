package com.yash.login.JWT.service;

import com.yash.login.JWT.Util.AuthUtil;
import com.yash.login.JWT.entity.User;
import com.yash.login.JWT.repo.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor

public class JwtAuthFilter extends OncePerRequestFilter {


    private final UserRepo userRepo;
    private AuthUtil authUtil;


    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{

        log.info("incomming request {}:", request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.split("Bearer")[1];
        String username = authUtil.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("checking user authentication {}:", username);
            User user = userRepo.findByUsername(username).orElse(null);

            UsernamePasswordAuthenticationToken token1= new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(token1);

            filterChain.doFilter(request, response);
        }}
        catch (Exception e){
            log.error("Error in JwtAuthFilter: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null,e);
        }
    }
}
