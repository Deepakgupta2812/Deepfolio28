package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Profile p WHERE p.id = :id AND p.resumeData IS NOT NULL")
    boolean existsResumeById(@Param("id") Long id);

    @Query("SELECT COUNT(p) > 0 FROM Profile p WHERE p.id = :id AND p.imageData IS NOT NULL")
    boolean existsImageById(@Param("id") Long id);
}
