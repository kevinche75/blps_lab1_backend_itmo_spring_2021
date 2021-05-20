package com.blps.app.controller;


import com.blps.app.model.Company;
import com.blps.app.model.User;
import com.blps.app.securty.Role;
import com.blps.app.service.CompanyService;
import com.blps.app.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final CompanyService companyService;
    private final ReportService reportService;

    public AdminController(CompanyService companyService, ReportService reportService){
        this.companyService = companyService;
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    @PutMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestParam(name = "name") String name){
        Company company = companyService.createCompany(name);
        if(company == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(company);
    }

    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    @PostMapping("/company/{name}")
    public ResponseEntity<Company> changeAccount(@RequestParam(name = "account") Double value,
                                                 @PathVariable(name = "name") String name){
        Company company = companyService.updateCompanyAccount(name, value);
        if(company == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(company);
    }

    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    @DeleteMapping("/company/{name}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable("name") String name){
        if(companyService.deleteCompany(name)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PreAuthorize("hasAnyRole('ADMIN_GLOBAL', 'ADMIN_COMPANY')")
    @PutMapping("/user")
    public ResponseEntity<User> createUser(@RequestParam(name = "login") String login,
                                           @RequestParam(name = "password") String password,
                                           @RequestParam(name = "name") String name,
                                           @RequestParam(name = "surname") String surname,
                                           @RequestParam(name = "passport") String passport,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "date") Date birthDate,
                                           @RequestParam(name = "boss") String bossLogin,
                                           @RequestParam(name = "companyName") String companyName,
                                           @RequestParam(name = "roles") Set<Role> roles){
        String creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = companyService.createUser(login, password, name, surname, passport, birthDate, bossLogin, companyName, creatorId, roles);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN_GLOBAL', 'ADMIN_COMPANY')")
    @PostMapping("/user")
    public ResponseEntity<User> changeUser(@RequestParam(name = "login") String login,
                                           @RequestParam(name = "password", required = false) String password,
                                           @RequestParam(name = "name", required = false) String name,
                                           @RequestParam(name = "surname", required = false) String surname,
                                           @RequestParam(name = "passport", required = false) String passport,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           @RequestParam(name = "date", required = false) Date birthDate,
                                           @RequestParam(name = "boss", required = false) String bossLogin,
                                           @RequestParam(name = "roles", required = false) Set<Role> roles){
        String creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = companyService.updateUser(login, password, name, surname, passport, birthDate, bossLogin, creatorId, roles);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN_GLOBAL', 'ADMIN_COMPANY')")
    @DeleteMapping("/user/{login}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("login") String login){
        String creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyService.deleteUser(login, creatorId)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PreAuthorize("hasAnyRole('ADMIN_GLOBAL', 'ADMIN_COMPANY')")
    @GetMapping("/{company}/users")
    public ResponseEntity<List<User>> getUsers(@PathVariable("company") String company){
        String creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> users = companyService.getUsers(company, creatorId);
        if(users != null){
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    @PostMapping("/task")
    public ResponseEntity<Boolean> createTask(@RequestParam(name = "login") String login,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           @RequestParam(name = "startStamp", required = false) Date startStamp,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           @RequestParam(name = "endStamp", required = false) Date endStamp,
                           @RequestParam(name = "placeFrom", required = false) String placeFrom,
                           @RequestParam(name = "placeTo", required = false) String placeTo
    ){
        if(reportService.createTask(login, startStamp, endStamp, placeFrom, placeTo)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
