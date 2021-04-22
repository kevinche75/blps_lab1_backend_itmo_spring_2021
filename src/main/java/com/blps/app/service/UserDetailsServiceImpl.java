package com.blps.app.service;

import com.blps.app.secure.UserDetailsImpl;
import com.blps.app.model.User;
import com.blps.app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(login);
        if (!user.isPresent()) throw new UsernameNotFoundException("User with login: "+  login + " not found");
        return new UserDetailsImpl(user.get());
    }
}
