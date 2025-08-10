package com.github.guilhermecustodionieto.finance_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;


import java.util.Arrays;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Finance API",
                version = "1.0.0",
                description = "API para gerenciamento de finanças pessoais, incluindo carteiras, transações e autenticação.",
                contact = @Contact(
                        name = "Guilherme Custódio",
                        email = "guilherme@email.com",
                        url = "https://github.com/guilhermecustodionieto"
                ),
                license = @License(
                        name = "Creative Commons",
                        url = "https://creativecommons.org/share-your-work/cclicenses/"
                )
        ),
        servers = {
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Finance API")
                        .description("API para gerenciamento financeiro pessoal com autenticação JWT")
                        .version("1.0.0"))
                .tags(Arrays.asList(
                        new Tag().name("Authentication").description("Endpoints para autenticação e registro de usuários"),
                        new Tag().name("User").description("Gerenciamento de usuários"),
                        new Tag().name("Wallet").description("Operações com carteiras financeiras"),
                        new Tag().name("Transaction History").description("Consulta ao histórico de transações"),
                        new Tag().name("Waste").description("Gerenciamento de despesas"),
                        new Tag().name("Recipe").description("Gerenciamento de receitas"),
                        new Tag().name("Transaction Category").description("Gerenciamento de categorias de transações")
                ));
    }
}
