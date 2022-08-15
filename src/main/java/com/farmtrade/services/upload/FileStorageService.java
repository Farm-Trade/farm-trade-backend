package com.farmtrade.services.upload;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileStorageService {
    String storeImage(MultipartFile img, String newImageName);
    void removeFile(String filePath);
    ResponseEntity<Resource> serveImage(String imgName, HttpServletRequest request);
}
