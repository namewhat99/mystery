package com.example.docker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuspectResourcePostDto {

    private String suspectName;
    private String suspectAge;
    private String suspectGender;
    private String suspectOccupation;
    private String suspectTrait;
    private String suspectImage;
}
