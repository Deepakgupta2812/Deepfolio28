package com.portfolio.deepak.controller;

import com.portfolio.deepak.service.CertificateService;
import com.portfolio.deepak.service.ContactService;
import com.portfolio.deepak.service.EventService;
import com.portfolio.deepak.service.ProjectService;
import com.portfolio.deepak.service.SkillService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ProjectService projectService;
    private final SkillService skillService;
    private final CertificateService certificateService;
    private final ContactService contactService;
    private final EventService eventService;

    public AdminController(
            ProjectService projectService,
            SkillService skillService,
            CertificateService certificateService,
            ContactService contactService,
            EventService eventService) {

        this.projectService = projectService;
        this.skillService = skillService;
        this.certificateService = certificateService;
        this.contactService = contactService;
        this.eventService = eventService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("activePage", "dashboard");

        int projectCount =
                projectService.getAllProjects().size();

        int skillCount =
                skillService.getAllSkills().size();

        int certificateCount =
                certificateService.getAllCertificates().size();

        int messageCount =
                contactService.getAllContacts().size();

        int eventCount = eventService.getAllEvents().size();

        int totalCount =
                projectCount
                        + skillCount
                        + certificateCount
                        + messageCount
                        + eventCount;

        model.addAttribute(
                "projectCount",
                projectCount
        );

        model.addAttribute(
                "skillCount",
                skillCount
        );

        model.addAttribute(
                "certificateCount",
                certificateCount
        );

        model.addAttribute(
                "messageCount",
                messageCount
        );
        model.addAttribute("eventCount", eventCount);

        model.addAttribute(
                "totalCount",
                totalCount
        );

        model.addAttribute(
                "recentContacts",
                contactService.getRecentContacts()
        );

        return "admin/dashboard";
    }
}