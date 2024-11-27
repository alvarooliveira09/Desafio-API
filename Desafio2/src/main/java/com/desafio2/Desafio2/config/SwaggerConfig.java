package com.desafio2.Desafio2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Agência de viagens (Desafio 2)", version = "1.0.0",
        description = "API Agência de viagens (Desafio 2)"))
public class SwaggerConfig { }
