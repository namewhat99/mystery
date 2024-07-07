package com.example.docker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "추리게임 API Docs",
                description = "추리게임 DM API 문서.",
                version = "1.0"
        )
)
@Configuration
public class SwaggerConfig {
}
