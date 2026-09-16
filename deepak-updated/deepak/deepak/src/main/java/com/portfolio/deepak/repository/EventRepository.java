package com.portfolio.deepak.repository;

import com.portfolio.deepak.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT COUNT(e) > 0 FROM Event e WHERE e.id = :id AND e.imageData IS NOT NULL")
    boolean existsImageById(@Param("id") Long id);
}
