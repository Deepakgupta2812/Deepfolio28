package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Admin;
import com.portfolio.deepak.repository.AdminRepository;
import com.portfolio.deepak.dto.PasswordChangeRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminPasswordService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminPasswordService(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {

        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean changePassword(
            String username,
            PasswordChangeRequest request) {

        Admin admin = adminRepository.findByUsername(username)
                .orElse(null);

        if (admin == null) {
            return false;
        }

        // Check current password
        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                admin.getPassword())) {

            return false;
        }

        // Check new password confirmation
        if (!request.getNewPassword().equals(
                request.getConfirmPassword())) {

            return false;
        }

        // Save new BCrypt password
        admin.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        adminRepository.save(admin);

        return true;
    }
}