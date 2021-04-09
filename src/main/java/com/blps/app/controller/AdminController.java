package com.blps.app.controller;


import com.blps.app.model.Company;
import com.blps.app.model.User;
import com.blps.app.service.CompanyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final CompanyService companyService;

    public AdminController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PutMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestParam(name = "name") String name){
        Company company = companyService.createCompany(name);
        if(company == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(company);
    }

    @PostMapping("/company/{name}")
    public ResponseEntity<Company> changeAccount(@RequestParam(name = "account") Double value,
                                                 @PathVariable(name = "name") String name){
        Company company = companyService.updateCompanyAccount(name, value);
        if(company == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/company/{name}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable("name") String name){
        if(companyService.deleteCompany(name)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
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
        User user = companyService.updateUser(login, password, name, surname, passport, birthDate, bossLogin);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/user/{login}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("login") String login){
        if(companyService.deleteUser(login)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/{company}/users")
    public ResponseEntity<List<User>> getUsers(@PathVariable("company") String company){
        List<User> users = companyService.getUsers(company);
        if(users != null){
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
