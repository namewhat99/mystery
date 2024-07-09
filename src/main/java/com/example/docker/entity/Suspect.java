package com.example.docker.entity;

import jakarta.persistence.*;
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

    @Column(length = 500)
    private String suspectTrait;

    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
