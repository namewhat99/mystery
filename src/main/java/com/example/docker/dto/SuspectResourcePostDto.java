package com.example.docker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuspectResourcePostDto {

    private String suspectName;
    private Integer suspectAge;
    private String suspectGender;
    private String suspectOccupation;
    private String suspectTrait;
    private String suspectImage;
}
