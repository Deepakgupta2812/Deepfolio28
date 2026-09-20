package com.portfolio.deepak.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    /** Short summary shown on the project card. */
    @Column(length = 2000)
    private String description;

    /** Comma separated list, e.g. "Java, Spring Boot, MySQL". */
    private String technologies;

    private String githubUrl;

    private String liveUrl;

    // ---------------------------------------------------------------
    // Fields added for the redesigned Projects section. All are
    // nullable so existing rows keep working untouched.
    // ---------------------------------------------------------------

    /** e.g. "Full Stack", "Backend", "Frontend". */
    private String category;

    /** Long-form overview shown on the project details page. */
    @Column(length = 5000)
    private String detailedDescription;

    /** One feature per line. */
    @Column(length = 2000)
    private String features;

    @Column(length = 2000)
    private String problemStatement;

    @Column(length = 2000)
    private String solution;

    @Column(length = 2000)
    private String architecture;

    /** Screenshot stored as a data URI, matching Certificate/Event. */
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageData;

    /** Lower numbers appear first. Null sorts last. */
    private Integer displayOrder;

    @Transient
    public boolean isHasGithub() {
        return githubUrl != null && !githubUrl.isBlank();
    }

    @Transient
    public boolean isHasLiveDemo() {
        return liveUrl != null && !liveUrl.isBlank();
    }

    @Transient
    public boolean isHasImage() {
        return imageData != null && !imageData.isBlank();
    }
}
