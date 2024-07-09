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
public class Story {

    @Id
    @GeneratedValue
    private Long id;

    private String place;

    private String time;

    private String weather;

    private String mainBackGroundImage;

    @Column(length = 500)
    private String storyLine;

    @Column(length = 3000)
    private String allStory;

    private LocalDate date;


}
