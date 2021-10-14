package com.teste.spring.boot.security.test.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.spring.boot.security.test.model.User;
import com.teste.spring.boot.security.test.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       /* String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        return  authenticationManager.authenticate(authenticationToken);*/
        try {

            User user = new ObjectMapper().readValue(request.getInputStream(),User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword(),new ArrayList<>()));
        }  catch (IOException e) {
            throw  new RuntimeException("Falha na autenticação do usuário");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC512(SecurityConstants.SECRET_KEY.getBytes());
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_tIME))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
