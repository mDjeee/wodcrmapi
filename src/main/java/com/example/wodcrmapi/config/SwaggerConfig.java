package com.example.wodcrmapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Title Example")
                        .description("API Description Example")
                        .version("API Version")
                        .contact(new Contact()
                                .name("API Contact Name")
                                .url("https://api.contact.url")
                                .email("medejeee@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("API External Documentation")
                        .url("https://api.external.documentation.url"));
    }
}