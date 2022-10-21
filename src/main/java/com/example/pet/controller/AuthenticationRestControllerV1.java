package com.example.pet.controller;

import com.example.pet.dto.AuthenticationRequestDTO;
import com.example.pet.entity.Person;
import com.example.pet.repository.PersonRepository;
import com.example.pet.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {
    private static final String INVALID_CREDS_MSG = "Invalid email/password combination";
    private static final String NOT_USER_EXIST_MSG = "User doesn't exist";
    private static final String TOKEN = "token";
    private static final String EMAIL = "email";

    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, PersonRepository personRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.personRepository = personRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

  @PostMapping("/login")
  public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDTO request) {
    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Person user = personRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(NOT_USER_EXIST_MSG));
        String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put(TOKEN, token);
        response.put(EMAIL, request.getEmail());
        return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      return new ResponseEntity<>(INVALID_CREDS_MSG, HttpStatus.FORBIDDEN);
    }
  }

  @PostMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
    securityContextLogoutHandler.logout(request, response, null);
  }
}
