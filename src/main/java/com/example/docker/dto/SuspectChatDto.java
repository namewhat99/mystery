package com.example.docker.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuspectChatDto {

    private String firstLine;
    private List<String> chatList;
}
