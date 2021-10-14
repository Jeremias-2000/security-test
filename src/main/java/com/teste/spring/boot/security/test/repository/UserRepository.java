package com.teste.spring.boot.security.test.repository;

import com.teste.spring.boot.security.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
