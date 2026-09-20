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

    // ---------------------------------------------------------------
    // Contact / social details, added so the public page can be driven
    // entirely from the database instead of hard-coded markup.
    // ---------------------------------------------------------------

    private String email;

    private String phone;

    private String location;

    private String githubUrl;

    private String linkedinUrl;

    /** Used by the GitHub section, e.g. "deepakgupta". */
    private String githubUsername;

    /** Optional external resume link, used when no PDF is uploaded. */
    private String resumeUrl;

    /** Short line under the hero heading. */
    @Column(length = 1000)
    private String heroText;

    /** Long-form text for the About section. */
    @Column(length = 4000)
    private String aboutText;

    @Transient
    public boolean isHasGithub() {
        return githubUrl != null && !githubUrl.isBlank();
    }

    @Transient
    public boolean isHasLinkedin() {
        return linkedinUrl != null && !linkedinUrl.isBlank();
    }

    @Transient
    public boolean isHasEmail() {
        return email != null && !email.isBlank();
    }
}
