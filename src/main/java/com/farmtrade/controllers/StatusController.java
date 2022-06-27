package com.farmtrade.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("status")
public class StatusController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Map<String, String> getStatus() {
        return new HashMap<String, String>() {{put("status", "OK");}};
    }
}
