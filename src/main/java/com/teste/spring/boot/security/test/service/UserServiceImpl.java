package com.teste.spring.boot.security.test.service;

import com.teste.spring.boot.security.test.model.User;
import com.teste.spring.boot.security.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<User> findUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
