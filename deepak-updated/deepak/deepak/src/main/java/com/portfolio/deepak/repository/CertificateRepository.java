package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    @Query("SELECT COUNT(c) > 0 FROM Certificate c WHERE c.id = :id AND c.imageData IS NOT NULL")
    boolean existsImageById(@Param("id") Long id);
}