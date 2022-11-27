package com.farmtrade.services.impl.upload;

import com.farmtrade.exceptions.FileNotFoundException;
import com.farmtrade.exceptions.FileStorageException;
import com.farmtrade.properties.FileStorageProperties;
import com.farmtrade.services.upload.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Set;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final Path fileStorageLocation;
    private final FileStorageProperties fileStorageProperties;

    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;

        this.fileStorageLocation = Paths.get(this.fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        Path imageStorageLocation = Paths.get(
                this.fileStorageProperties.getUploadDir() + this.fileStorageProperties.getImageDir()
        ).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(imageStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Деректорія для файлу не може бути створенена", ex);
        }
    }

    @Override
    public String storeImage(MultipartFile img, String newImageName) {
        String imgName = StringUtils.cleanPath(Objects.requireNonNull(img.getOriginalFilename()));

        Set<String> types = fileStorageProperties.getImageTypes();
        String imageType = types.stream().filter(type -> imgName.endsWith("." + type))
                .findFirst().orElseThrow(() -> new FileStorageException(
                        "Фото має тип який не підтримується, підтримуються тільки наступні типи " + types
                ));

        String imagePath = fileStorageProperties.getImageDir() + newImageName + "." + imageType;
        return storeFile(img, imagePath);
    }

    @Override
    public void removeFile(String filePath) {
        Path targetLocation = this.fileStorageLocation.resolve(filePath);
        try {
            Files.deleteIfExists(targetLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Невдалось видалити файл спробуйте ще раз!", ex);
        }
    }


    @Override
    public ResponseEntity<Resource> serveImage(String imgName, HttpServletRequest request) {
        Resource resource = loadFileAsResource(fileStorageProperties.getImageDir() + imgName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Невдалось визначити тип файлу.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String storeFile(MultipartFile file, String newFileName) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Не вдалось зберегти файл. Спробуйте ще раз!", ex);
        }
    }

    private Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Файл не знайдено " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Файл не знайдено " + fileName, ex);
        }
    }
}
