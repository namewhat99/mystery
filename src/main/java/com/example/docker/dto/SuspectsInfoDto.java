package com.example.docker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuspectsInfoDto {

    private Integer suspectNumber;
    private String suspectName;
    private String suspectSex;
    private String suspectImageUrl;
    private Integer suspectAge;
    private String job;
    private String suspectInfo;


}
