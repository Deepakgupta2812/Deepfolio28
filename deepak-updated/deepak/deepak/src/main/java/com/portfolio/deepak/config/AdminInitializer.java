package com.portfolio.deepak.config;

import com.portfolio.deepak.entity.Admin;
import com.portfolio.deepak.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public AdminInitializer(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.username:Deepak}") String adminUsername,
            @Value("${app.admin.password:admin123}") String adminPassword) {

        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    /**
     * Creates the first admin account if none exists. The password is
     * always stored BCrypt-hashed, never in plain text, and the values
     * come from configuration rather than being hard-coded.
     */
    @Override
    public void run(String... args) {

        if (adminRepository.findByUsername(adminUsername).isEmpty()) {

            Admin admin = new Admin();

            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole("ADMIN");

            adminRepository.save(admin);

            System.out.println(
                    "Default admin account '" + adminUsername
                            + "' created. Change the password from Admin > Settings."
            );
        }
    }
}
