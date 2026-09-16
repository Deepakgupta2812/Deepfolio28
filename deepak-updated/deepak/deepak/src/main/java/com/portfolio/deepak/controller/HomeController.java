package com.portfolio.deepak.controller;

import com.portfolio.deepak.service.CertificateService;
import com.portfolio.deepak.service.EventService;
import com.portfolio.deepak.service.ProfileService;
import com.portfolio.deepak.service.ProjectService;
import com.portfolio.deepak.service.SkillService;
import com.portfolio.deepak.service.VisitorStatsService;
import com.portfolio.deepak.entity.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProjectService projectService;
    private final SkillService skillService;
    private final CertificateService certificateService;
    private final EventService eventService;
    private final ProfileService profileService;
    private final VisitorStatsService visitorStatsService;

    public HomeController(
            ProjectService projectService,
            SkillService skillService,
            CertificateService certificateService,
            EventService eventService,
            ProfileService profileService,
            VisitorStatsService visitorStatsService) {

        this.projectService = projectService;
        this.skillService = skillService;
        this.certificateService = certificateService;
        this.eventService = eventService;
        this.profileService = profileService;
        this.visitorStatsService = visitorStatsService;
    }

    @GetMapping("/")
    public String home(Model model) {

        // Count this page load as a visit to the portfolio.
        visitorStatsService.recordVisit();

        model.addAttribute(
                "projects",
                projectService.getAllProjects()
        );

        model.addAttribute(
                "skills",
                skillService.getAllSkills()
        );

        model.addAttribute(
                "certificates",
                certificateService.getAllCertificates()
        );
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("profile", profileService.getProfile());

        model.addAttribute("contact", new Contact());
        model.addAttribute("hasResume", profileService.hasResume());
        model.addAttribute("profileHasImage", profileService.hasImage());

        return "index";
    }
}