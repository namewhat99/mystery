package com.example.docker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuspectChatRequestDto {

    private Integer userId;
    private String question;
}
