package com.example.docker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500)
    private String resultContent1;

    private String resultImageUrl1;

    @Column(length = 500)
    private String resultContent2;

    private String resultImageUrl2;

    private String criminal;

    @Column(length = 500)
    private String criminalSaying;

    @Column(length = 500)
    private String caseBackground;

    private LocalDate date;
}
