package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.VisitorStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorStatsRepository extends JpaRepository<VisitorStats, Long> {
}
