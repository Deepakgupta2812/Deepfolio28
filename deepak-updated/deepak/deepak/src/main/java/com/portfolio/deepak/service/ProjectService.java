package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Project;
import com.portfolio.deepak.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /** Ordered by displayOrder, then id. */
    public List<Project> getAllProjects() {
        return projectRepository.findAllOrdered();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    /**
     * Splits the newline-separated "features" text into a list so the
     * templates can render it without any string handling of their own.
     */
    public List<String> featureList(Project project) {
        if (project == null || project.getFeatures() == null || project.getFeatures().isBlank()) {
            return List.of();
        }
        return project.getFeatures().lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();
    }

    /** Splits the comma-separated technologies string into individual tags. */
    public List<String> techList(Project project) {
        if (project == null || project.getTechnologies() == null || project.getTechnologies().isBlank()) {
            return List.of();
        }
        return java.util.Arrays.stream(project.getTechnologies().split(","))
                .map(String::trim)
                .filter(tag -> !tag.isEmpty())
                .toList();
    }
}
