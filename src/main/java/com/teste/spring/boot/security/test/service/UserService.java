package com.teste.spring.boot.security.test.service;

import com.teste.spring.boot.security.test.model.User;

import java.util.List;

public interface UserService {
    List<User> findUsers();
    User saveUser(User user);
    void deleteUser(Long id);

}
