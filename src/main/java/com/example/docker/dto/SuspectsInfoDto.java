package com.example.docker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuspectsInfoDto {

    private Long suspectNumber;
    private String suspectName;
    private String suspectGender;
    private String suspectImageUrl;
    private Integer suspectAge;
    private String suspectOccupation;
    private String suspectInfo;


}
