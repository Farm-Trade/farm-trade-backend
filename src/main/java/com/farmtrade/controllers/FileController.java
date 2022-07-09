package com.farmtrade.controllers;

import com.farmtrade.services.upload.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FileController {
    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/img/{imgName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imgName, HttpServletRequest request) {
        return fileStorageService.serveImage(imgName, request);
    }
}
