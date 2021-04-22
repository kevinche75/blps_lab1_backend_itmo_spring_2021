package com.blps.app.controller;

import com.blps.app.model.User;
import com.blps.app.securty.JWTUtils;
import com.blps.app.service.CompanyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth/")
@RestController
public class AuthController {

    @Value("${jwt.header}")
    private String JWTHeader;

    private final AuthenticationManager authenticationManager;
    private final JWTUtils JWTUtils;
    private final CompanyService companyService;

    public AuthController(AuthenticationManager authenticationManager, JWTUtils JWTUtils, CompanyService companyService) {
        this.authenticationManager = authenticationManager;
        this.JWTUtils = JWTUtils;
        this.companyService = companyService;
    }

    //login
    @CrossOrigin
    @PostMapping("/user")
    public ResponseEntity<User> login(@RequestParam(name = "login") String login,
                                      @RequestParam(name = "password") String password) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = JWTUtils.generateToken(login);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(JWTHeader, jwtToken);
        User user = companyService.getUser(login);
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }
}
