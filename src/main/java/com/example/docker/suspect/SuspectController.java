package com.example.docker.suspect;

import com.example.docker.dto.ResponseDto;
import com.example.docker.dto.SuspectsInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

@RestController
@RequestMapping("api/suspects")
public class SuspectController {

    @GetMapping
    @Operation(summary = "용의자들 인적사항" , description = "각 용의자들의 인적사항을 반환한다. 데이터들 중 suspectNumber 는 나중에 다른 api 를 사용할때 그대로 전달해주기만 하면 될듯")
    public ResponseDto<ArrayList<SuspectsInfoDto>> findSuspectsInfo(){
        return new ResponseDto<ArrayList<SuspectsInfoDto>>(200 , "Good" , null);
    }

    @GetMapping(value = "{id}" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "용의자 심문하기 첫 멘트" , description = "Figma 상의 (진수 그놈이랑 나랑은,,,,,) 부분, 응답은 Stream 의 형태로 반환")
    public ResponseDto<Flux<String>> getSuspectSpecificInfo(@PathVariable("id") String id){
        return new ResponseDto<Flux<String>>(200 , "Good" , null);
    }

    @PostMapping(value = "{id}/question" ,  produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "용의자 심문", description = "사용자의 심문에 대한 답변을 반환한다, 응답은 Stream 의 형태로 반환")
    public ResponseDto<Flux<String>> getSuspectAnswer(@PathVariable("id") String id){
        return new ResponseDto<Flux<String>>(200 , "Good" , null);
    }
}
