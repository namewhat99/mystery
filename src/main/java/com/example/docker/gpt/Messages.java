package com.example.docker.gpt;

import lombok.Builder;

@Builder
public class Messages {

    private String role;
    private String content;
}
