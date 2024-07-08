package com.example.docker.user;

import com.example.docker.dto.ChanceCheckDto;
import com.example.docker.dto.NicknameDuplicateDto;
import com.example.docker.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/duplicate")
    @Operation(summary = "닉네임 중복 체크")
    public ResponseDto<NicknameDuplicateDto> checkNicknameDuplicate(){
        return new ResponseDto<>(200 , "Good" , null);
    }

    @GetMapping("/chance")
    @Operation(summary = "심문 횟수 남았는지 여부 체크")
    public ResponseDto<ChanceCheckDto> checkChanceLeft(){
        return new ResponseDto<>(200 , "Good" , null);
    }
}


