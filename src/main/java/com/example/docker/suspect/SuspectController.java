package com.example.docker.suspect;

import com.example.docker.dto.ResponseDto;
import com.example.docker.dto.SuspectChatDto;
import com.example.docker.dto.SuspectChatRequestDto;
import com.example.docker.dto.SuspectsInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/suspects")
@RequiredArgsConstructor
public class SuspectController {

    private final SuspectService suspectService;
    @GetMapping
    @Operation(summary = "용의자들 인적사항" , description = "각 용의자들의 인적사항을 반환한다. 데이터들 중 suspectNumber 는 나중에 다른 api 를 사용할때 그대로 전달해주기만 하면 될듯")
    public ResponseDto<List<SuspectsInfoDto>> findSuspectsInfo(){
        List<SuspectsInfoDto> suspects = this.suspectService.findSuspects();
        return new ResponseDto<List<SuspectsInfoDto>>(200 , "Good" , suspects);
    }

    @GetMapping(value = "{suspectNumber}")
    @Operation(summary = "용의자 심문하기" , description = "심문하기 첫 멘트 및 대화내용")
    public ResponseDto<SuspectChatDto> getSuspectSpecificInfo(@PathVariable("suspectNumber") Integer suspectNumber , @RequestParam(value = "userId" , required = true) Integer userId){
        SuspectChatDto suspectChat = this.suspectService.findSuspectChat(suspectNumber, Long.valueOf(userId));
        return new ResponseDto<SuspectChatDto>(200 , "Good" , suspectChat);
    }

    @PostMapping(value = "{suspectNumber}/question" ,  produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "용의자 심문", description = "사용자의 심문에 대한 답변을 반환한다, 응답은 Stream 의 형태로 반환")
    public ResponseDto<Flux<String>> getSuspectAnswer(@PathVariable("suspectNumber") Integer suspectNumber , @RequestBody SuspectChatRequestDto suspectChatRequestDto){

        return new ResponseDto<Flux<String>>(200 , "Good" , null);
    }
}
