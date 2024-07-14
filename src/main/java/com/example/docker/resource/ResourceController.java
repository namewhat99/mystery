package com.example.docker.resource;


import com.example.docker.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResourceController {

    private final S3Service s3Service;
    private final ResourceService resourceService;

    @PostMapping("/suspect")
    @Operation(summary = "용의자 정보 받는 api")
    public ResponseDto<String> uploadSuspectResource(@RequestBody SuspectResourcePostDto suspectResourcePostDto){
        String imageUrl = uploadFile(suspectResourcePostDto.getSuspectImage());
        suspectResourcePostDto.setSuspectImage(imageUrl);
        this.resourceService.uploadSuspectResource(suspectResourcePostDto);
        return new ResponseDto<>(200 , "good" , null);
    }

    @PostMapping("/evidence")
    @Operation(summary = "증거 정보 받는 api")
    public ResponseDto<String> uploadEvidenceResource(@RequestBody EvidenceResourcePostDto evidenceResourcePostDto){
        String imageUrl = uploadFile(evidenceResourcePostDto.getEvidenceImage());
        evidenceResourcePostDto.setEvidenceImage(imageUrl);
        this.resourceService.uploadEvidenceResource(evidenceResourcePostDto);
        return new ResponseDto<>(200 , "good" , null);
    }

    @PostMapping("/story")
    @Operation(summary = "스토리 정보 받는 api")
    public ResponseDto<String> uploadStoryResource(@RequestBody StoryResourcePostDto storyResourcePostDto){
        String imageUrl = uploadFile(storyResourcePostDto.getMainBackgroundImage());
        storyResourcePostDto.setMainBackgroundImage(imageUrl);
        this.resourceService.uploadStoryResource(storyResourcePostDto);
        return new ResponseDto<>(200 , "good" , null);
    }

    @PostMapping("/result")
    @Operation(summary = "결과 정보 받는 api" , description = "resultContent 는 범인 이유  , resultImage 는 이유의 이미지")
    public ResponseDto<String> uploadResultResource(@RequestBody ResultResourcePostDto resultResourcePostDto){
        String contentImage1 = uploadFile(resultResourcePostDto.getResultImage1());
        String contentImage2 = uploadFile(resultResourcePostDto.getResultImage2());
        resultResourcePostDto.setResultImage1(contentImage1);
        resultResourcePostDto.setResultImage2(contentImage2);
        this.resourceService.uploadResultResource(resultResourcePostDto);
        return new ResponseDto<>(200 , "good" , null);
    }



    private String uploadFile(String base64EncodedFile){
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedFile);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            return s3Service.uploadFile(inputStream, generateUUID());
        } catch (IllegalArgumentException e) {
            return "not ok";
        }
    }

    private String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-" , "");
    }

}
