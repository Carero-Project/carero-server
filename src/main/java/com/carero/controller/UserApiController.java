package com.carero.controller;

import com.carero.domain.user.User;
import com.carero.dto.user.UserDto;
import com.carero.dto.user.UserPasswordChangeDto;
import com.carero.service.UserService;
import com.carero.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @GetMapping("/me")
    public UserDto myInfo(){
        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));

        return new UserDto(user);
    }

    @PostMapping("/me/password")
    public ResponseEntity<String> changeMyPassword(@Valid @RequestBody UserPasswordChangeDto userPasswordChangeDto){

        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));

        Long id = userService.changePassword(user, userPasswordChangeDto);

        return ResponseEntity.ok("Done");
    }

}
