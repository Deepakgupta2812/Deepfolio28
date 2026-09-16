package com.portfolio.deepak.config;

import com.portfolio.deepak.entity.Admin;
import com.portfolio.deepak.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {

        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (adminRepository.findByUsername("Deepak").isEmpty()) {

            Admin admin = new Admin();

            admin.setUsername("Deepak");
            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );
            admin.setRole("ADMIN");

            adminRepository.save(admin);

            System.out.println(
                    "Default admin account created successfully."
            );
        }
    }
}