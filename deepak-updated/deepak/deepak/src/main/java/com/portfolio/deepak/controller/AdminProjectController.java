package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Project;
import com.portfolio.deepak.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/projects")
public class AdminProjectController {

    private final ProjectService projectService;

    public AdminProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Show all projects
    @GetMapping
    public String getAllProjects(Model model) {

        model.addAttribute("activePage", "projects");

        model.addAttribute(
                "projects",
                projectService.getAllProjects()
        );

        return "admin/projects";
    }

    // Show Add Project form
    @GetMapping("/add")
    public String showAddProjectForm(Model model) {

        model.addAttribute("activePage", "projects");

        model.addAttribute("project", new Project());

        return "admin/project-form";
    }

    // Save new project
    @PostMapping("/save")
    public String saveProject(
            @ModelAttribute Project project) {

        projectService.saveProject(project);

        return "redirect:/admin/projects";
    }

    // Show Edit Project form
    @GetMapping("/edit/{id}")
    public String showEditProjectForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("activePage", "projects");

        model.addAttribute(
                "project",
                projectService.getProjectById(id)
        );

        return "admin/project-form";
    }

    // Delete project
    @PostMapping("/delete/{id}")
    public String deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);

        return "redirect:/admin/projects";
    }
}