package com.portfolio.deepak.controller;

import com.portfolio.deepak.dto.PasswordChangeRequest;
import com.portfolio.deepak.service.AdminPasswordService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminPasswordController {

    private final AdminPasswordService adminPasswordService;

    public AdminPasswordController(
            AdminPasswordService adminPasswordService) {
        this.adminPasswordService = adminPasswordService;
    }

    // Show Change Password Page
    @GetMapping("/admin/change-password")
    public String showChangePasswordPage(Model model) {

        model.addAttribute(
                "activePage",
                "change-password"
        );

        model.addAttribute(
                "passwordChangeRequest",
                new PasswordChangeRequest()
        );

        return "admin/change-password";
    }

    // Process Change Password
    @PostMapping("/admin/change-password")
    public String changePassword(
            @Valid @ModelAttribute("passwordChangeRequest")
            PasswordChangeRequest request,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/change-password";
        }

        String username = authentication.getName();

        boolean changed = adminPasswordService.changePassword(
                username,
                request
        );

        if (!changed) {
            model.addAttribute(
                    "error",
                    "Current password is incorrect or new passwords do not match."
            );

            return "admin/change-password";
        }

        model.addAttribute(
                "success",
                "Password changed successfully!"
        );

        model.addAttribute(
                "passwordChangeRequest",
                new PasswordChangeRequest()
        );

        return "admin/change-password";
    }

}