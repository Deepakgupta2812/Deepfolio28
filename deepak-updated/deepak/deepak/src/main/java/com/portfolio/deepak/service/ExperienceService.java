package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Experience;
import com.portfolio.deepak.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> getAllExperience() {
        return experienceRepository.findAllByOrderByStartDateDesc();
    }

    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience entry not found"));
    }

    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }

    public long count() {
        return experienceRepository.count();
    }
}
