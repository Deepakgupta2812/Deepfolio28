package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Experience;
import com.portfolio.deepak.service.ExperienceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin/experience")
public class AdminExperienceController {

    private final ExperienceService experienceService;

    public AdminExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    /**
     * An empty date input submits "", which is not a valid LocalDate.
     * Treating it as null lets the admin leave "End date" blank to mark
     * a role as current.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue((text == null || text.isBlank()) ? null : LocalDate.parse(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value == null ? "" : value.toString();
            }
        });
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("activePage", "experience");
        model.addAttribute("experienceList", experienceService.getAllExperience());
        return "admin/experience";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("activePage", "experience");
        model.addAttribute("experience", new Experience());
        return "admin/experience-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "experience");
        model.addAttribute("experience", experienceService.getExperienceById(id));
        return "admin/experience-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Experience experience,
                       RedirectAttributes redirectAttributes) {
        experienceService.saveExperience(experience);
        redirectAttributes.addFlashAttribute("success", "Experience entry saved.");
        return "redirect:/admin/experience";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        experienceService.deleteExperience(id);
        redirectAttributes.addFlashAttribute("success", "Experience entry deleted.");
        return "redirect:/admin/experience";
    }
}
