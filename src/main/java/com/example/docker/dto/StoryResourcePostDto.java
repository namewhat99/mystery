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
    private String allStory;
    private String storyLine;
}
