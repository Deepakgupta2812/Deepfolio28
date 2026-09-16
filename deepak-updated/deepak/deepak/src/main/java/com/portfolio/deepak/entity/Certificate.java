package com.portfolio.deepak.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "certificates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String organization;

    private LocalDate issueDate;

    private String credentialUrl;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageData;
}
