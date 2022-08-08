package com.farmtrade.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    public OpenAPIConfiguration(@Value("${server.port}") Integer port) {
        Logger logger = LoggerFactory.getLogger(OpenAPIConfiguration.class);

        String url = "http://localhost:" + port + "/swagger-ui/index.html";
        logger.info("\n***********************\nOpenAPI Documentation started on " + url + "\n***********************");
    }
}
