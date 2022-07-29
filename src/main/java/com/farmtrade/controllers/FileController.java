package com.farmtrade.controllers;

import com.farmtrade.services.upload.FileStorageService;
import com.farmtrade.services.upload.FileStorageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Tag(name = "File Controller", description = "The File API")
public class FileController {
    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/api/img/{imgName:.+}")
    @Operation(summary = "Get image served by server")
    public ResponseEntity<Resource> getImage(@PathVariable String imgName, HttpServletRequest request) {
        return fileStorageService.serveImage(imgName, request);
    }
}
