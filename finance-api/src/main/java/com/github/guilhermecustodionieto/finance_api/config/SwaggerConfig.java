package com.github.guilhermecustodionieto.finance_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

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
}
