package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Education;
import com.portfolio.deepak.service.EducationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/education")
public class AdminEducationController {

    private final EducationService educationService;

    public AdminEducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("activePage", "education");
        model.addAttribute("educationList", educationService.getAllEducation());
        return "admin/education";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("activePage", "education");
        model.addAttribute("education", new Education());
        return "admin/education-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "education");
        model.addAttribute("education", educationService.getEducationById(id));
        return "admin/education-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Education education,
                       RedirectAttributes redirectAttributes) {
        educationService.saveEducation(education);
        redirectAttributes.addFlashAttribute("success", "Education entry saved.");
        return "redirect:/admin/education";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        educationService.deleteEducation(id);
        redirectAttributes.addFlashAttribute("success", "Education entry deleted.");
        return "redirect:/admin/education";
    }
}
