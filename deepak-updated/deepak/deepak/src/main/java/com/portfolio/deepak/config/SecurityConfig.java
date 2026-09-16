package com.portfolio.deepak.config;

import com.portfolio.deepak.service.AdminUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final AdminUserDetailsService adminUserDetailsService;

    public SecurityConfig(AdminUserDetailsService adminUserDetailsService) {
        this.adminUserDetailsService = adminUserDetailsService;
    }

    // BCrypt Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security Configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .userDetailsService(adminUserDetailsService)

                .authorizeHttpRequests(auth -> auth

                        // Public resources
                        .requestMatchers(
                                "/",
                                "/contact",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/image/**",
                                "/resume",
                                "/admin/login"
                        ).permitAll()

                        // Admin pages
                        .requestMatchers("/admin/**").authenticated()

                        .anyRequest().permitAll()
                )

                // Custom Login
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl(
                                "/admin/dashboard",
                                true
                        )
                        .failureUrl("/admin/login?error=true")
                        .permitAll()
                )

                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl(
                                "/admin/login?logout=true"
                        )
                        .permitAll()
                );

        return http.build();
    }
}