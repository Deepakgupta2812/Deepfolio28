package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Admin;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminService adminService;

    public AdminUserDetailsService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Admin admin = adminService.getAdminByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException(
                    "Admin not found: " + username
            );
        }

        return new User(
                admin.getUsername(),
                admin.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + admin.getRole()
                        )
                )
        );
    }
}