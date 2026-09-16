package com.portfolio.deepak.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio_profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private String name = "Deepak Gupta";

    private String headline = "Java & Spring Boot Developer";

    @Column(length = 2000)
    private String bio;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageData;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] resumeData;

    private String resumeFileName;

    private String resumeContentType;

    /**
     * Hex color code (e.g. #f4f4f0) applied to the profile name, headline
     * and bio text on the public portfolio page. Editable from the admin panel.
     */
    private String textColor = "#f4f4f0";
}
