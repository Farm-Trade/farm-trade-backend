package com.farmtrade.constants;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.beans.factory.annotation.Value;

public class SwaggerConstants {
    public final static String SECURITY_SCHEME_NAME = "Bearer Authentication";
    public final static String SECURITY_SCHEME = "bearer";
    public final static String SECURITY_SCHEME_BEARER_FORMAT = "JWT";
    public final static String INFO_TITLE = "Farm Trade API";
    public final static String INFO_VERSION = "v1";
    public final static String INFO_DESCRIPTION = "Farm Trade Documentation";
}
