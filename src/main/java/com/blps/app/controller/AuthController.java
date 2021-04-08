package com.blps.app.controller;

import com.blps.app.model.User;
import com.blps.app.secure.JWTUtils;
import com.blps.app.service.AuthService;
import com.blps.app.service.CompanyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/api/auth/")
@RestController
public class AuthController {

    @Value("$(jwt.header)")
    private String JWTHeader;

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JWTUtils JWTUtils;
    private final CompanyService companyService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JWTUtils JWTUtils, CompanyService companyService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
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
        User user = authService.getUser(login);
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }

    @PutMapping("/user")
    public ResponseEntity<User> createUser(@RequestParam(name = "login") String login,
                                           @RequestParam(name = "password") String password,
                                           @RequestParam(name = "name") String name,
                                           @RequestParam(name = "surname") String surname,
                                           @RequestParam(name = "passport") String passport,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "date") Date birthDate,
                                           @RequestParam(name = "boss") String bossLogin,
                                           @RequestParam(name = "companyName") String companyName
    ){
        User user = companyService.createUser(login, password, name, surname, passport, birthDate, bossLogin, companyName);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
