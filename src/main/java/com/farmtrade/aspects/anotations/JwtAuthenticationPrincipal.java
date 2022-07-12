package com.farmtrade.aspects.anotations;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtAuthenticationPrincipal {
}
