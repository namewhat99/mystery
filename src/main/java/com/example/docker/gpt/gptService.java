package com.example.docker.gpt;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
public class gptService {

    private final WebClient webClient;

    @Autowired
    public gptService(WebClient webClient){
        this.webClient = webClient;
    }
    public Flux<String> sendRequestToGpt(){

        Gson gson = new Gson();

        // JSON 객체 생성
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4o");
        requestBody.addProperty("stream" , true);

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", "Can you write a new novel about crime?");
        messages.add(message);

        requestBody.add("messages", messages);

        String jsonRequestBody = gson.toJson(requestBody);
        // Flux 의 동작방식에 대해 찾아보기
        Flux<String> response = this.webClient.post()
                .bodyValue(jsonRequestBody)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class);

        return response;
    }
}
