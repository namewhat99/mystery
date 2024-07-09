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

    @Column(length = 5000)
    private String allStory;

    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }


}
