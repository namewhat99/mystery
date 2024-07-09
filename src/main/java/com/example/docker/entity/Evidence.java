package com.example.docker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Evidence {

    @Id
    @GeneratedValue
    private Long id;

    private String evidenceName;
    private String evidenceInfo;
    private String evidenceImageUrl;

    private LocalDate date;
}
