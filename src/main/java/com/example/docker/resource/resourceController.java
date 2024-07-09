package com.example.docker.resource;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping("/api/resource")
public class resourceController {

    private final S3Service s3Service;

    public resourceController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping
    public String uploadBase64File(@RequestBody Base64Request base64Request) {
        if (base64Request.getFile() == null || base64Request.getFile().isEmpty()) {
            return "not ok";
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Request.getFile());
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            String fileUrl = s3Service.uploadFile(inputStream, base64Request.getFileName());
            System.out.println("fileUrl = " + fileUrl);
            return "ok";
        } catch (IllegalArgumentException e) {
            return "not ok";
        }
    }

    private String uploadFile(String base64EncodedFile){
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedFile);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            String fileUrl = s3Service.uploadFile(inputStream, base64EncodedFile);
            System.out.println("fileUrl = " + fileUrl);
            return "ok";
        } catch (IllegalArgumentException e) {
            return "not ok";
        }
    }
}
