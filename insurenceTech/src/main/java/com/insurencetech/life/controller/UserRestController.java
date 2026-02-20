package com.insurencetech.life.controller;

import com.insurencetech.life.entity.UserLogin;
import com.insurencetech.life.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserRestController {
    @Autowired
    private UserLoginService loginService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserLogin user) {

        boolean status = loginService.saveUser(user);
        if (status) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("status", "SUCCESS",
                            "message", "User Registration Successful")
            );
        } else
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "User Registration Failed",
                            "status", "FAILED")
                    );

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword());
            Authentication auth = authManager.authenticate(token);
            boolean status = auth.isAuthenticated();

            if (status) {
                return ResponseEntity.ok(Map.of(
                        "status", "SUCCESS",
                        "message", "Login SuccessFull"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                        "status", "SUCCESS",
                        "message", "Login SuccessFull"
                ));
            }
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "status", "FAILED",
                            "message", "Invalid credentials"
                    ));
        }
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
