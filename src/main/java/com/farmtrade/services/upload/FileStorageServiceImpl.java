package com.farmtrade.services.upload;

import com.farmtrade.exceptions.FileNotFoundException;
import com.farmtrade.exceptions.FileStorageException;
import com.farmtrade.properties.FileStorageProperties;
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
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeImage(MultipartFile img, String newImageName) {
        String imgName = StringUtils.cleanPath(Objects.requireNonNull(img.getOriginalFilename()));

        Set<String> types = fileStorageProperties.getImageTypes();
        String imageType = types.stream().filter(type -> imgName.endsWith("." + type))
                .findFirst().orElseThrow(() -> new FileStorageException(
                        "Image has unsupported type, only following types allowed " + types
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
            throw new FileStorageException("Could not remove file. Please try again!", ex);
        }
    }


    @Override
    public ResponseEntity<Resource> serveImage(String imgName, HttpServletRequest request) {
        Resource resource = loadFileAsResource(fileStorageProperties.getImageDir() + imgName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
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
            throw new FileStorageException("Could not store file. Please try again!", ex);
        }
    }

    private Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }
}
