package com.farmtrade.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/status")
@Tag(name = "Status Controller", description = "The Status API")
public class StatusController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get application status")
    public Map<String, String> getStatus() {
        return new HashMap<String, String>() {{put("status", "OK");}};
    }
}
