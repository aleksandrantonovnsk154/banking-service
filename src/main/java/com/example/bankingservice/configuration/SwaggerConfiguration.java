package com.example.bankingservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI apiInfo() {
        Info apiInfo = new Info()
                .title("Сервис банка REST APIs")
                .description("APIs сервиса банка.")
                .version("1.0.0");

        return new OpenAPI().info(apiInfo);
    }
}