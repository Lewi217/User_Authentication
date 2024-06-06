package dev.lewi.backend.security.config;

import dev.lewi.backend.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    // Inject the custom AppUserService
    private final AppUserService appUserService;

    // Define the security filter chain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection (only do this if you understand the implications)
                .csrf(csrf -> csrf.disable())
                // Configure URL authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        // Allow public access to URLs under /api/v*/registration/**
                        .requestMatchers("/api/v*/registration/**").permitAll()
                        // Require authentication for any other request
                        .anyRequest().authenticated()
                )
                // Configure form-based login
                .formLogin(formLogin -> formLogin
                        // Specify the custom login page URL and allow access to it
                        .loginPage("/login").permitAll()
                )
                // Configure logout functionality
                .logout(logout -> logout.permitAll());

        // Build the security filter chain
        return http.build();
    }

    // Define the password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCryptPasswordEncoder for hashing passwords
        return new BCryptPasswordEncoder();
    }

    // Configure authentication manager with custom user details service
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserService).passwordEncoder(passwordEncoder());
    }
}
