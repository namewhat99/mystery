package com.example.docker.gpt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController("/gpt")
public class gptController {

    private final gptService gptService;
    public gptController(gptService gptService){
        this.gptService = gptService;
    }

    @GetMapping("gpt")
    public Flux<String> getGptRequest(){
        return this.gptService.sendRequestToGpt();
    }
}
