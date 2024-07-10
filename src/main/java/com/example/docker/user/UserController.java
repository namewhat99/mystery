package com.example.docker.user;

import com.example.docker.dto.ChanceCheckDto;
import com.example.docker.dto.UserAddDto;
import com.example.docker.dto.ResponseDto;
import com.example.docker.dto.UserNicknameDto;
import com.example.docker.entity.User;
import com.example.docker.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "닉네임 등록" , description = "닉네임 중복이면 400 에러 던짐")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "굳"),
            @ApiResponse(responseCode = "400", description = "닉네임 중복")
    })
    public ResponseDto<UserAddDto> checkNicknameDuplicate(@RequestBody UserNicknameDto userNicknameDto){
        User user = this.userService.addDailyUser(userNicknameDto.getNickname());
        return new ResponseDto<>(200 , "Good" , new UserAddDto(user.getId()));
    }

    @GetMapping("/chance")
    @Operation(summary = "심문 횟수 남았는지 여부 체크" , description = "심문 횟수 초과로 심문하면 400 에러 던짐 , 해당 id 를 갖는 유저 없으면 404 던짐")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Join Us!"),
            @ApiResponse(responseCode = "400", description = "심문 횟수 초과"),
            @ApiResponse(responseCode = "404", description = "해당 userId 를 갖는 유저가 존재하지 않음")
    })
    public ResponseDto<ChanceCheckDto> checkChanceLeft(@RequestParam(value = "userId" , required = true) Long userId){
        Boolean isChanceLeft = this.userService.checkUserChanceLeft(userId);
        ChanceCheckDto chanceCheckDto = new ChanceCheckDto(isChanceLeft);
        return new ResponseDto<>(200 , "Good" , chanceCheckDto);
    }
}


