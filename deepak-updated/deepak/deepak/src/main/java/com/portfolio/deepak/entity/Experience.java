package com.portfolio.deepak.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
public class Experience {

    private static final DateTimeFormatter MONTH_YEAR =
            DateTimeFormatter.ofPattern("MMM yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String position;

    private String company;

    private String companyUrl;

    private String location;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    /** Null means the role is current. */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Column(length = 2000)
    private String description;

    /** Comma separated, e.g. "Java, Spring Boot, MySQL". */
    private String technologies;

    @Transient
    public String getPeriod() {
        if (startDate == null) {
            return endDate == null ? "" : endDate.format(MONTH_YEAR);
        }
        String from = startDate.format(MONTH_YEAR);
        String to = endDate == null ? "Present" : endDate.format(MONTH_YEAR);
        return from + " – " + to;
    }

    @Transient
    public boolean isCurrent() {
        return endDate == null;
    }
}
