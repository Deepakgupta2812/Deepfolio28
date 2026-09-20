package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Project;
import com.portfolio.deepak.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/admin/projects")
public class AdminProjectController {

    private static final long MAX_IMAGE_BYTES = 5L * 1024 * 1024;

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

    /**
     * Saves a new or edited project. The screenshot follows the same
     * base64 data-URI approach already used for certificates and events,
     * so no new storage mechanism is introduced.
     */
    @PostMapping("/save")
    public String saveProject(
            @ModelAttribute Project project,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "removeImage", required = false) String removeImage,
            RedirectAttributes redirectAttributes) throws IOException {

        if (image != null && !image.isEmpty()) {

            String contentType = image.getContentType();

            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Project image must be an image file.");
            }
            if (image.getSize() > MAX_IMAGE_BYTES) {
                throw new IllegalArgumentException("Project image must be smaller than 5 MB.");
            }

            project.setImageData("data:" + contentType + ";base64,"
                    + Base64.getEncoder().encodeToString(image.getBytes()));

        } else if ("true".equalsIgnoreCase(removeImage)) {
            // Admin explicitly chose to delete the current screenshot.
            project.setImageData(null);

        } else if (project.getId() != null) {
            // No new upload: keep whatever is already stored.
            project.setImageData(projectService.getProjectById(project.getId()).getImageData());
        }

        projectService.saveProject(project);
        redirectAttributes.addFlashAttribute("success", "Project saved.");

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
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("success", "Project deleted.");

        return "redirect:/admin/projects";
    }
}
