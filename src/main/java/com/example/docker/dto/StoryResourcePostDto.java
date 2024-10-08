package com.example.docker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryResourcePostDto {

    private String weather;
    private String time;
    private String place;
    private String mainBackgroundImage;
    private String storyLine;
    private String victimName;
    private Integer victimAge;
    private String victimGender;
    private String victimOccupation;
}
