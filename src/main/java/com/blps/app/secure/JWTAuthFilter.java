package com.blps.app.secure;

import com.blps.app.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {

    @Value("$(jwt.header)")
    private String JWTHeader;

    private final JWTUtils JWTUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public JWTAuthFilter(JWTUtils utils, UserDetailsServiceImpl userDetailsService){
        this.JWTUtils = utils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = servletRequest.getHeader(JWTHeader);
        if(token != null && JWTUtils.validateToken(token)){
            String login = JWTUtils.getLoginFromToken(token);
            try{
                UserDetails userDetails = userDetailsService.loadUserByUsername(login);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e){
                servletResponse.setStatus(401);
                servletResponse.sendError(401, "Authorization failed. Invalid token");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
