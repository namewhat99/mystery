package com.example.docker.main;


import com.example.docker.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @GetMapping()
    @Operation(summary = "첫 페이지" , description = "사건 배경 반환")
    public ResponseDto<MainPageContentDto> findMainPageContent(){
        return new ResponseDto<>(200 , "Good" , null);
    }

    @GetMapping("/story")
    @Operation(summary = "첫 페이지 이후 사건 개요 페이지" , description = "사건 개요 반환")
    public ResponseDto<MainPageStoryDto> findMainPageStory(){
        return new ResponseDto<>(200, "Good" , null);
    }

    @GetMapping("/result")
    @Operation(summary = "게임 종료 후 결과 데이터" , description = "사건 전말 , 용의자 이미지, 이유 반환")
    public ResponseDto<ResultDto> findResult(){
        return new ResponseDto<>(200 , "Good" , null);
    }

    @GetMapping("/image")
    @Operation(summary = "배경 이미지 api" , description = "배경 이미지 반환")
    public ResponseDto<ImageDto> findBackgroundImage(){
        return new ResponseDto<ImageDto>(200 , "Good" , null);
    }
}
