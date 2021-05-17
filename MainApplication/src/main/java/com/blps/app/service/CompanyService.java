package com.blps.app.service;

import com.blps.app.model.Company;
import com.blps.app.model.User;
import com.blps.app.repository.CompanyRepository;
import com.blps.app.repository.UserRepository;
import com.blps.app.securty.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository, PasswordEncoder bcryptEncoder) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
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
                           String bossLogin, String companyName, String creatorId, Set<Role> roles){
        if(userRepository.existsById(login)){
            return null;
        }
        Optional<User> creator = userRepository.findById(creatorId);
        Optional<User> bossOptional = userRepository.findById(bossLogin);
        Optional<Company> companyOptional = companyRepository.findById(companyName);

        if(bossOptional.isPresent() && companyOptional.isPresent() && creator.isPresent()){
            User user = new User();
            user.setLogin(login);
            user.setPassword(bcryptEncoder.encode(password));
            user.setName(name);
            user.setSurname(surname);
            user.setPassport(passport);
            user.setBirthday(birthDate);
            user.setBoss(bossOptional.get());
            user.setCompany(creator.get().getCompany() == null ? companyOptional.get() : creator.get().getCompany());
            user.setRoles(roles);
            userRepository.saveAndFlush(user);
            return user;
        }
        return null;
    }

    public User getUser(String login){
        Optional<User> user = userRepository.findById(login);
        return user.orElse(null);
    }

    public User updateUser(String login, String password, String name,
                           String surname, String passport, Date birthDate,
                           String bossLogin, String creatorId, Set<Role> roles){
        Optional<User> userOptional = userRepository.findById(login);
        Optional<User> creator = userRepository.findById(creatorId);
        if(userOptional.isPresent() && creator.isPresent() && (creator.get().getCompany() == null || creator.get().getCompany().equals(userOptional.get().getCompany()))){
            User user = userOptional.get();
            if(password != null){
                user.setPassword(bcryptEncoder.encode(password));
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
                if(userRepository.findById(bossLogin).get().getRoles().contains(Role.ROLE_MANAGER)) {
                    user.setBoss(userRepository.getOne(bossLogin));
                } else return null;
            }
            if(roles != null){
                user.setRoles(roles);
            }
            userRepository.saveAndFlush(user);
            return user;
        }
        return null;
    }

    public boolean deleteUser(String name, String creatorId){
        Optional<User> creator = userRepository.findById(creatorId);
        Optional<User> user = userRepository.findById(name);
        if(user.isPresent() && creator.isPresent() && (creator.get().getCompany()==null || creator.get().getCompany().equals(user.get().getCompany()))){
            userRepository.deleteById(name);
            return true;
        }
        return false;
    }

    public List<User> getUsers(String companyName, String creatorId){
        Optional<User> creator = userRepository.findById(creatorId);
        Optional<Company> companyOptional = companyRepository.findById(companyName);
        if(!creator.isPresent() || (creator.get().getCompany() != null && !creator.get().getCompany().getName().equals(companyName))){
            return null;
        }
        return companyOptional.map(Company::getUsers).orElse(null);
    }


}