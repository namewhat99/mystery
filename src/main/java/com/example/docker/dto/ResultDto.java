package com.example.docker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultDto {

    private String resultContent1;
    private String resultImage1;
    private String resultContent2;
    private String resultImage2;
    private String caseBackground;
    private String criminal;
    private String criminalImage;
    private String criminalSaying;
    private String allStory;

}
