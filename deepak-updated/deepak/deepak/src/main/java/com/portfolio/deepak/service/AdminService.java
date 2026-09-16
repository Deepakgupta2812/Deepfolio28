package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Admin;
import com.portfolio.deepak.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin getAdminByUsername(String username) {

        return adminRepository.findByUsername(username)
                .orElse(null);
    }
}