package com.portfolio.deepak.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio_visitor_stats")
@Getter
@Setter
@NoArgsConstructor
public class VisitorStats {

    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private long visitCount = 0L;
}
