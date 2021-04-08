package com.blps.app.config;

import com.blps.app.service.CustomUserDetailsService;
import com.blps.app.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthFilter implements Filter {

    @Value("$(jwt.header)")
    private String JWTHeader;

    @Autowired
    private JWTUtils JWTUtils;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = ((HttpServletRequest) servletRequest).getHeader(JWTHeader);
        if(token != null && JWTUtils.validateToken(token)){
            String email = JWTUtils.getLoginFromToken(token);
            try{
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e){
                ((HttpServletResponse)servletResponse).setStatus(401);
                ((HttpServletResponse)servletResponse).sendError(401, "Authorization failed. Invalid token");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
