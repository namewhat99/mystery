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
public class Story {

    @Id
    @GeneratedValue
    private Long id;

    private String place;

    private String time;

    private String weather;

    private String mainBackGroundImage;

    @Column(length = 1000)
    private String storyLine;

    private String victimName;

    private Integer victimAge;

    private String victimGender;

    private String victimOccupation;

    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }


}
