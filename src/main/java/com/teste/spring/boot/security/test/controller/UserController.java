package com.teste.spring.boot.security.test.controller;

import com.teste.spring.boot.security.test.model.User;
import com.teste.spring.boot.security.test.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/")
    public String home(){
        return "Home";
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return  ResponseEntity.ok(userService.findUsers());
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        userService.deleteUser(id);
        return  ResponseEntity.ok().build();
    }
}
