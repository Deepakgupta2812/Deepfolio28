package com.portfolio.deepak.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private LocalDate eventDate;

    private String venue;

    @Column(length = 2000)
    private String description;

    private String eventUrl;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageData;
}
