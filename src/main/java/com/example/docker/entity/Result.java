package com.example.docker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1000)
    private String resultContent1;

    private String resultImageUrl1;

    @Column(length = 1000)
    private String resultContent2;

    private String resultImageUrl2;

    private String criminal;

    @Column(length = 1000)
    private String criminalSaying;

    @Column(length = 1000)
    private String caseBackground;

    @Column(length = 5000)
    private String allStory;

    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
