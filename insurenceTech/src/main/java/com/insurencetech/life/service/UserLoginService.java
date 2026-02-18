package com.insurencetech.life.service;

import com.insurencetech.life.entity.UserLogin;
import com.insurencetech.life.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserLoginService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public boolean saveUser(UserLogin user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        UserLogin savedUser = userRepository.save(user);
        return savedUser.getId() != null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserLogin userObject = userRepository.findByEmail(email);
        return new User(userObject.getEmail(), userObject.getPassword(), Collections.emptyList());
    }
}
