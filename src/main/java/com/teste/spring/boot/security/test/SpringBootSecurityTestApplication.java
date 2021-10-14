package com.teste.spring.boot.security.test;

import com.teste.spring.boot.security.test.model.User;
import com.teste.spring.boot.security.test.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBootSecurityTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityTestApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService){
		return args -> {
			userService.saveUser(new User(null,"Jose Marinho","teste1@gmail.com","password123",43));
			userService.saveUser(new User(null,"Antonia Gonçalves","teste2@gmail.com","12345678@",55));
			userService.saveUser(new User(null,"Antonio Pereira","teste3@gmail.com","passwordxza@#",27));
			userService.saveUser(new User(null,"Cristian Aguiar","teste4@gmail.com","passwordxyz",25));
		};
	}
}
