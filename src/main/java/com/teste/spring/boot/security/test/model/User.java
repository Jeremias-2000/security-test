package com.teste.spring.boot.security.test.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Email
    private String email;
    @JsonProperty(access = WRITE_ONLY )
    private String password;
    private int age;

}
