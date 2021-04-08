package com.blps.app.service;

import com.blps.app.model.User;
import com.blps.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUser(String login){
        Optional<User> user = userRepository.findById(login);
        return user.orElse(null);
    }

    public boolean validateLogin(String login){
        return !userRepository.existsById(login);
    }
}
