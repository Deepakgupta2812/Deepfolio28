package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    /** Most recent role first. */
    List<Experience> findAllByOrderByStartDateDesc();
}
