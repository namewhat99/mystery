package com.example.docker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Suspect {

    @Id
    @GeneratedValue
    private Long id;

    private String suspectName;
    private Integer suspectAge;
    private String suspectGender;
    private String suspectOccupation;
    private String suspectTrait;

    private LocalDate date;
}
