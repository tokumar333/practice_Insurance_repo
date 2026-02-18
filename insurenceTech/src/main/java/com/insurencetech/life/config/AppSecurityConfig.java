package com.insurencetech.life.config;

import com.insurencetech.life.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
    @Autowired
    private UserLoginService userLoginService;

    @Bean
    public BCryptPasswordEncoder getBCryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userLoginService);
        authProvider.setPasswordEncoder(getBCryptEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfiguration) {
        return authConfiguration.getAuthenticationManager();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests((req) -> {
            req.requestMatchers("/register","/login").permitAll()
                    .anyRequest().authenticated();
        });
        return http.csrf(c -> c.disable()).build();
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF only for H2 console and register endpoint
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/register")
                )
                // Allow H2 console frames
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                // Permit all access to register & H2 console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }*/

}



