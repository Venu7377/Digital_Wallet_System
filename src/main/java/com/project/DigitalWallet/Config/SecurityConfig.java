package com.project.DigitalWallet.Config;

import com.project.DigitalWallet.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    PropertyReader propertyReader;


    @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {
        String user_username = propertyReader.getProperty("user.username");
        String user_password = propertyReader.getProperty("user.password");
        String user_role = propertyReader.getProperty("user.role");

        UserDetails user = User.builder()
                .username(user_username)
                .password(passwordEncoder().encode(user_password))
                .roles(user_role)
                .build();
        String admin_username = propertyReader.getProperty("admin.username");
        String admin_password = propertyReader.getProperty("admin.password");
        String admin_role = propertyReader.getProperty("admin.role");
        UserDetails admin = User.builder()
                .username(admin_username)
                .password(passwordEncoder().encode(admin_password))
                .roles(admin_role)
                .build();
        return new InMemoryUserDetailsManager(user,admin);
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