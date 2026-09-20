package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {

    /** Most recent qualification first. */
    List<Education> findAllByOrderByStartYearDesc();
}
