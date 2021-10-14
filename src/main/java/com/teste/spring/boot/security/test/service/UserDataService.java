package com.teste.spring.boot.security.test.service;

import com.teste.spring.boot.security.test.model.User;
import com.teste.spring.boot.security.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserDataService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user.equals(null)){
            throw new UsernameNotFoundException("User not found in database");
        }
        return new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(),new ArrayList<>());
    }
}
