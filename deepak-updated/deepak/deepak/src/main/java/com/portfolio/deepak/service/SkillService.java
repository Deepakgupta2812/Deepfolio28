package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Skill;
import com.portfolio.deepak.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkillService {

    /**
     * Categories are rendered in this order when they exist. Anything
     * stored under a different category name is appended afterwards, so
     * existing rows in the database are never hidden.
     */
    private static final List<String> CATEGORY_ORDER = List.of(
            "Programming Languages",
            "Backend",
            "Frontend",
            "Databases",
            "Tools"
    );

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    /**
     * Groups skills by category for the redesigned Skills section.
     * Uncategorised skills are collected under "Other".
     */
    public Map<String, List<Skill>> getSkillsGroupedByCategory() {

        Map<String, List<Skill>> grouped = new LinkedHashMap<>();

        for (String category : CATEGORY_ORDER) {
            grouped.put(category, new ArrayList<>());
        }

        for (Skill skill : skillRepository.findAll()) {
            String category = (skill.getCategory() == null || skill.getCategory().isBlank())
                    ? "Other"
                    : skill.getCategory().trim();
            grouped.computeIfAbsent(category, key -> new ArrayList<>()).add(skill);
        }

        // Drop the preset categories that have no skills stored yet.
        grouped.values().removeIf(List::isEmpty);

        grouped.values().forEach(list ->
                list.sort(Comparator.comparing(
                        Skill::getName,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))));

        return grouped;
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found"));
    }

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}
