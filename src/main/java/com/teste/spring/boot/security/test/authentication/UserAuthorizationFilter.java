package com.teste.spring.boot.security.test.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.spring.boot.security.test.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.teste.spring.boot.security.test.security.SecurityConstants.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    public UserAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (request.getServletPath().equals("/login") ){
           chain.doFilter(request,response);
       }
        else {
          String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
            if (!authorizationHeader.equals(null) && authorizationHeader.startsWith(HEADER_PREFIX)){
                try {
                    String token = authorizationHeader.substring(HEADER_PREFIX.length());
                    Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY.getBytes());
                    String user = JWT
                            .require(algorithm).build()
                            .verify(token)
                            .getSubject();
                    if (user.equals(null)){
                        return;
                    }

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                     new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    chain.doFilter(request,response);
                }catch (NullPointerException e){
                    System.out.println("usuario nulo" + e.getMessage());
                }
                catch (Exception e){
                    response.setHeader("error",e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    //response.sendError(FORBIDDEN.value());

                    //aqui ele ta pegando os erros de autenticação no caso o token se foi passado certo tbm
                    Map<String,String> error = new HashMap<>();
                    error.put("error_message",e.getMessage());

                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),error);
                }
            }else {
                chain.doFilter(request,response);
            }
      }

    }


}
