package com.project.DigitalWallet.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    Environment env;

    @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {
        UserDetails user1 = User.builder()
                .username(Objects.requireNonNull(env.getProperty("user1.username")))
                .password(passwordEncoder().encode(env.getProperty("user1.password")))
                .roles(env.getProperty("user1.role"))
                .build();

        UserDetails user2 = User.builder()
                .username(Objects.requireNonNull(env.getProperty("user2.username")))
                .password(passwordEncoder().encode(env.getProperty("user2.password")))
                .roles(env.getProperty("user2.role"))
                .build();
        return new InMemoryUserDetailsManager(user1,user2);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth )-> auth
                        .requestMatchers("/digitalWalletSystem/v1/wallet/user/**")
                        .hasRole("User")
                        .requestMatchers("/digitalWalletSystem/v1/wallet/admin/**")
                        .hasRole("Admin")
                        .requestMatchers("/h2-ui/**").permitAll()
                        .anyRequest()
                        .authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.headers(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}