package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Skill;
import com.portfolio.deepak.service.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/skills")
public class AdminSkillController {

    private final SkillService skillService;

    public AdminSkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    // Show all skills
    @GetMapping
    public String getAllSkills(Model model) {

        model.addAttribute("activePage", "skills");

        model.addAttribute(
                "skills",
                skillService.getAllSkills()
        );

        return "admin/skills";
    }

    // Show Add Skill form
    @GetMapping("/add")
    public String showAddSkillForm(Model model) {

        model.addAttribute("activePage", "skills");

        model.addAttribute("skill", new Skill());

        return "admin/skill-form";  // IMPORTANT
    }

    // Save Skill
    @PostMapping("/save")
    public String saveSkill(@ModelAttribute Skill skill) {

        skillService.saveSkill(skill);

        return "redirect:/admin/skills";
    }

    // Show Edit Skill form
    @GetMapping("/edit/{id}")
    public String showEditSkillForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("activePage", "skills");

        model.addAttribute(
                "skill",
                skillService.getSkillById(id)
        );

        return "admin/skill-form"; // IMPORTANT
    }

    // Delete Skill
    @PostMapping("/delete/{id}")
    public String deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return "redirect:/admin/skills";
    }
}