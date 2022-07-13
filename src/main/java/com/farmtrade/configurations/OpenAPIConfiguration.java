package com.farmtrade.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static com.farmtrade.constants.SwaggerConstants.*;

@Configuration
@OpenAPIDefinition(info = @Info(title = INFO_TITLE, version = INFO_VERSION, description = INFO_DESCRIPTION))
@SecurityScheme(
        name = SECURITY_SCHEME_NAME,
        scheme = SECURITY_SCHEME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = SECURITY_SCHEME_BEARER_FORMAT
)
public class OpenAPIConfiguration {
}
