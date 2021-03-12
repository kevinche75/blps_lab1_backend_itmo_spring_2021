package com.blps.app.controller;


import com.blps.app.model.Company;
import com.blps.app.model.User;
import com.blps.app.service.CompanyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final CompanyService companyService;

    public AdminController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PutMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestParam(name = "name") String name){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/company")
    public ResponseEntity<Company> changeAccount(@RequestParam(name = "account") Double value){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/company/{name}")
    public ResponseEntity<Company> deleteCompany(@PathVariable("name") String name){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/company/{name}")
    public ResponseEntity<Company> getCompany(@PathVariable("name") String name){
        return ResponseEntity.ok(null);
    }

    @PutMapping("/user")
    public ResponseEntity<User> createUser(@RequestParam(name = "login") String login,
                                           @RequestParam(name = "password") String password,
                                           @RequestParam(name = "name") String name,
                                           @RequestParam(name = "surname") String surname,
                                           @RequestParam(name = "passport") String passport,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                 @RequestParam(name = "date") Date birthDate,
                                           @RequestParam(name = "boss") String bossLogin,
                                           @RequestParam(name = "companyName") String companyName
                                                 ){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/user")
    public ResponseEntity<User> changeUser(@RequestParam(name = "login") String login,
                                           @RequestParam(name = "password", required = false) String password,
                                           @RequestParam(name = "name", required = false) String name,
                                           @RequestParam(name = "surname", required = false) String surname,
                                           @RequestParam(name = "passport", required = false) String passport,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           @RequestParam(name = "date", required = false) Date birthDate,
                                           @RequestParam(name = "boss", required = false) String bossLogin
    ){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/user/{login}")
    public ResponseEntity<User> deleteUser(@PathVariable("login") String login){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<User> getUser(@PathVariable("login") String login){
        return ResponseEntity.ok(null);
    }
}
