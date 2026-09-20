package com.portfolio.deepak.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** e.g. "B.Tech — Computer Science & Engineering". */
    @Column(nullable = false)
    private String degree;

    /** e.g. "Bansal Institute of Engineering & Technology, Lucknow". */
    private String institution;

    /** e.g. "AKTU". */
    private String board;

    private Integer startYear;

    /** Null means "present". */
    private Integer endYear;

    /** e.g. "CGPA 8.2" — optional. */
    private String grade;

    @Column(length = 1000)
    private String description;

    @Transient
    public String getPeriod() {
        if (startYear == null && endYear == null) {
            return "";
        }
        String from = startYear == null ? "" : String.valueOf(startYear);
        String to = endYear == null ? "Present" : String.valueOf(endYear);
        return from.isEmpty() ? to : from + " – " + to;
    }
}
