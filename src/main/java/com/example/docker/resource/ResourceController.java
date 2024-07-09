package com.example.docker.resource;


import com.example.docker.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    private final S3Service s3Service;

    public ResourceController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/suspect")
    @Operation(summary = "용의자 정보 받는 api")
    public ResponseDto<String> uploadSuspectResource(@RequestBody SuspectResourcePostDto suspectResourcePostDto){
        return new ResponseDto<>(200 , "good" , null);
    }

    @PostMapping("/evidence")
    @Operation(summary = "증거 정보 받는 api")
    public ResponseDto<String> uploadEvidenceResource(@RequestBody EvidenceResourcePostDto evidenceResourcePostDto){
        return new ResponseDto<>(200 , "good" , null);
    }

    @PostMapping("/story")
    @Operation(summary = "스토리 정보 받는 api")
    public ResponseDto<String> uploadStoryResource(@RequestBody StoryResourcePostDto storyResourcePostDto){
        return new ResponseDto<>(200 , "good" , null);
    }

    @PostMapping("/result")
    @Operation(summary = "결과 정보 받는 api" , description = "resultContent 는 범인 이유  , resultImage 는 이유의 이미지")
    public ResponseDto<String> uploadResultResource(@RequestBody ResultResourcePostDto resultResourcePostDto){
        return new ResponseDto<>(200 , "good" , null);
    }

//    @PostMapping
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
