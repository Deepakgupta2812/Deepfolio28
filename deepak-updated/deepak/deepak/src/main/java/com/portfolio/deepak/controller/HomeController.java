package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Contact;
import com.portfolio.deepak.entity.Project;
import com.portfolio.deepak.service.CertificateService;
import com.portfolio.deepak.service.EducationService;
import com.portfolio.deepak.service.EventService;
import com.portfolio.deepak.service.ExperienceService;
import com.portfolio.deepak.service.ProfileService;
import com.portfolio.deepak.service.ProjectService;
import com.portfolio.deepak.service.SkillService;
import com.portfolio.deepak.service.VisitorStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    private final ProjectService projectService;
    private final SkillService skillService;
    private final CertificateService certificateService;
    private final EventService eventService;
    private final ProfileService profileService;
    private final VisitorStatsService visitorStatsService;
    private final EducationService educationService;
    private final ExperienceService experienceService;

    public HomeController(
            ProjectService projectService,
            SkillService skillService,
            CertificateService certificateService,
            EventService eventService,
            ProfileService profileService,
            VisitorStatsService visitorStatsService,
            EducationService educationService,
            ExperienceService experienceService) {

        this.projectService = projectService;
        this.skillService = skillService;
        this.certificateService = certificateService;
        this.eventService = eventService;
        this.profileService = profileService;
        this.visitorStatsService = visitorStatsService;
        this.educationService = educationService;
        this.experienceService = experienceService;
    }

    @GetMapping("/")
    public String home(Model model) {

        // Count this page load as a visit to the portfolio.
        visitorStatsService.recordVisit();

        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("skills", skillService.getAllSkills());
        model.addAttribute("skillGroups", skillService.getSkillsGroupedByCategory());
        model.addAttribute("certificates", certificateService.getAllCertificates());
        model.addAttribute("educationList", educationService.getAllEducation());
        model.addAttribute("experienceList", experienceService.getAllExperience());
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("profile", profileService.getProfile());
        model.addAttribute("hasResume", profileService.hasResume());
        model.addAttribute("profileHasImage", profileService.hasImage());

        // Keep a rejected submission (flashed back from ContactController)
        // instead of replacing it with a blank form.
        if (!model.containsAttribute("contact")) {
            model.addAttribute("contact", new Contact());
        }

        return "index";
    }

    /**
     * Dedicated details view for a single project. Rendered with
     * Thymeleaf so it works without any client-side framework and is
     * directly linkable (good for sharing and for SEO).
     */
    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {

        Project project = projectService.getProjectById(id);

        model.addAttribute("project", project);
        model.addAttribute("features", projectService.featureList(project));
        model.addAttribute("techs", projectService.techList(project));
        model.addAttribute("profile", profileService.getProfile());

        List<Project> others = projectService.getAllProjects().stream()
                .filter(other -> !other.getId().equals(id))
                .limit(3)
                .toList();
        model.addAttribute("otherProjects", others);

        return "project-details";
    }
}
