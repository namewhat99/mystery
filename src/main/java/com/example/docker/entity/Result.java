package com.example.docker.entity;

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

    private String resultContent1;
    private String resultImageUrl1;
    private String resultContent2;
    private String resultImageUrl2;

    private String criminal;

    private String eventReason;

    private String criminalSaying;
    private LocalDate date;
}
