package com.example.docker.user;

import com.example.docker.dto.ChanceCheckDto;
import com.example.docker.dto.UserAddDto;
import com.example.docker.dto.ResponseDto;
import com.example.docker.dto.UserNicknameDto;
import com.example.docker.entity.User;
import com.example.docker.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "닉네임 등록" , description = "닉네임 중복이면 400 에러 던짐")
    public ResponseDto<UserAddDto> checkNicknameDuplicate(@RequestBody UserNicknameDto userNicknameDto){
        User user = this.userService.addDailyUser(userNicknameDto.getNickname());

        return new ResponseDto<>(200 , "Good" , new UserAddDto(user.getId()));
    }

    @GetMapping("/chance")
    @Operation(summary = "심문 횟수 남았는지 여부 체크")
    public ResponseDto<ChanceCheckDto> checkChanceLeft(){
        return new ResponseDto<>(200 , "Good" , null);
    }
}


