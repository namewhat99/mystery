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
public class Suspect {

    @Id
    @GeneratedValue
    private Long id;

    private String suspectName;

    private Integer suspectAge;

    private String suspectGender;

    private String suspectOccupation;

    @Column(length = 1000)
    private String suspectSpeciality;

    @Column(length = 1000)
    private String suspectTrait;

    private String suspectImage;

    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
