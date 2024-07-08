package com.example.docker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SuspectChatDto {

    private String firstLine;
    private ArrayList<String> chatList;
}
