package com.example.docker.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${gpt.apiKey}")
    private String openApiKey;
    private final String apiUrl = "https://api.openai.com/v1/chat/completions";

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION , "Bearer " + openApiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE , "application/json")
                .baseUrl(apiUrl)
                .build();
    }
}
