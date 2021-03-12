package com.blps.app.service;

import com.blps.app.model.Company;
import com.blps.app.model.User;
import com.blps.app.repository.CompanyRepository;
import com.blps.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company createCompany(String name){
        if(companyRepository.existsById(name)){
            return null;
        }

        Company company = new Company();
        company.setName(name);
        company.setAccount(0.0);
        companyRepository.saveAndFlush(company);
        return company;
    }

    public Company updateCompanyAccount(String name, double amount){
        Optional<Company> companyOptional = companyRepository.findById(name);
        if(companyOptional.isPresent() && amount > 0){
            Company company = companyOptional.get();
            company.setAccount(company.getAccount() + amount);
            companyRepository.saveAndFlush(company);
            return company;
        }
        return null;
    }

    public boolean deleteCompany(String name){
        if(companyRepository.existsById(name)){
            companyRepository.deleteById(name);
            return true;
        }
        return false;
    }

    public User createUser(String login, String password, String name,
                           String surname, String passport, Date birthDate,
                           String bossLogin, String companyName){
        if(userRepository.existsById(login)){
            return null;
        }
        Optional<User> bossOptional = userRepository.findById(bossLogin);
        Optional<Company> companyOptional = companyRepository.findById(companyName);
        if(bossOptional.isPresent() && companyOptional.isPresent()){
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            user.setPassport(passport);
            user.setBirthday(birthDate);
            user.setBoss(bossOptional.get());
            user.setCompany(companyOptional.get());
            userRepository.saveAndFlush(user);
            return user;
        }
        return null;
    }

    public User updateUser(String login, String password, String name,
                           String surname, String passport, Date birthDate,
                           String bossLogin){
        Optional<User> userOptional = userRepository.findById(login);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(password != null){
                user.setPassword(password);
            }
            if(name != null){
                user.setName(name);
            }
            if(surname != null){
                user.setSurname(surname);
            }
            if(passport != null){
                user.setPassport(passport);
            }
            if(birthDate != null){
                user.setBirthday(birthDate);
            }
            if(bossLogin != null && userRepository.existsById(bossLogin)){
                user.setBoss(userRepository.getOne(bossLogin));
            }
        }
        return null;
    }

    public boolean deleteUser(String name){
        if(userRepository.existsById(name)){
            userRepository.deleteById(name);
            return true;
        }
        return false;
    }

    public List<User> getUsers(String companyName){
        Optional<Company> companyOptional = companyRepository.findById(companyName);
        return companyOptional.map(Company::getUsers).orElse(null);
    }


}
