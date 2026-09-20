package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Education;
import com.portfolio.deepak.repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getAllEducation() {
        return educationRepository.findAllByOrderByStartYearDesc();
    }

    public Education getEducationById(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education entry not found"));
    }

    public Education saveEducation(Education education) {
        return educationRepository.save(education);
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }

    public long count() {
        return educationRepository.count();
    }
}
