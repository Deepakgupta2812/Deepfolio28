package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Ordered for the public portfolio: explicit displayOrder first,
     * projects without one fall to the end in insertion order.
     */
    @Query("SELECT p FROM Project p ORDER BY CASE WHEN p.displayOrder IS NULL THEN 1 ELSE 0 END, p.displayOrder ASC, p.id ASC")
    List<Project> findAllOrdered();
}
