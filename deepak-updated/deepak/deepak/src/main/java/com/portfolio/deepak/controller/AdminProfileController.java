package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Profile;
import com.portfolio.deepak.service.ProfileService;
import com.portfolio.deepak.service.VisitorStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/admin/profile")
public class AdminProfileController {

    private final ProfileService profileService;
    private final VisitorStatsService visitorStatsService;

    public AdminProfileController(ProfileService profileService, VisitorStatsService visitorStatsService) {
        this.profileService = profileService;
        this.visitorStatsService = visitorStatsService;
    }

    @GetMapping
    public String showProfile(Model model) {
        model.addAttribute("profile", profileService.getProfile());
        model.addAttribute("activePage", "profile");
        model.addAttribute("visitCount", visitorStatsService.getVisitCount());
        return "admin/profile";
    }

    @PostMapping("/save")
    public String saveProfile(
            @ModelAttribute Profile profile,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "removeImage", required = false) String removeImage,
            @RequestParam(value = "resume", required = false) MultipartFile resume)
            throws IOException {
        Profile existing = profileService.getProfile();

        if (image != null && !image.isEmpty()) {
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Profile image must be an image file.");
            }
            if (image.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Profile image must be smaller than 5 MB.");
            }
            profile.setImageData("data:" + contentType + ";base64,"
                    + Base64.getEncoder().encodeToString(image.getBytes()));
        } else if ("true".equalsIgnoreCase(removeImage)) {
            // Admin explicitly chose to delete the current profile image.
            profile.setImageData(null);
        } else {
            profile.setImageData(existing.getImageData());
        }

        if (resume != null && !resume.isEmpty()) {
            String contentType = resume.getContentType();
            if (!"application/pdf".equalsIgnoreCase(contentType)) {
                throw new IllegalArgumentException("Resume must be a PDF file.");
            }
            if (resume.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Resume must be smaller than 5 MB.");
            }
            profile.setResumeData(resume.getBytes());
            profile.setResumeFileName(resume.getOriginalFilename());
            profile.setResumeContentType(contentType);
        } else {
            profile.setResumeData(existing.getResumeData());
            profile.setResumeFileName(existing.getResumeFileName());
            profile.setResumeContentType(existing.getResumeContentType());
        }

        profileService.saveProfile(profile);
        return "redirect:/admin/profile";
    }
}
