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
