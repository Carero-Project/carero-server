package com.carero.controller;

import com.carero.advice.exception.MyUserNotFoundException;
import com.carero.domain.user.User;
import com.carero.dto.response.RestResponse;
import com.carero.dto.user.UserDto;
import com.carero.dto.user.UserPasswordChangeDto;
import com.carero.service.ResponseService;
import com.carero.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"User"})
@RequestMapping("/user")
public class UserApiController {
    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 실시한다.")
    @PostMapping("/signup")
    public RestResponse signup(@Valid @RequestBody UserDto userDto) {
        return responseService.getSingleResponse(userService.signup(userDto.toEntity()));
    }

    @GetMapping("/me")
    public RestResponse myInfo(){
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        return responseService.getSingleResponse(new UserDto(user));
    }

    @PostMapping("/me/password")
    public RestResponse changeMyPassword(@Valid @RequestBody UserPasswordChangeDto userPasswordChangeDto){

        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Long id = userService.changePassword(user, userPasswordChangeDto);

        return responseService.getSuccessResponse();
    }

}
