package com.lumahdev.reservasalasapi.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("Reserva de Salas API")
                        .description("API Rest para a reserva de salas, contando com diversas funcionalidades: CRUD de Usuários e Salas, realização/cancelamento de Reservas.")
                        .contact(new Contact()
                                .name("Lumah Pereira")
                                .url("https://github.com/lumahdev")));
    }
}
