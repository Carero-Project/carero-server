package com.carero.controller;

import com.carero.dto.user.UserDto;
import com.carero.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"User"})
@RequestMapping("/user")
public class UserApiController {
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 실시한다.")
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto.toEntity()));
    }

}
