package com.example.docker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MainPageContentDto {

    private String weather;
    private String place;
    private String time;
}
