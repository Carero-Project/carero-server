package com.carero.dto.user;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserPasswordChangeDto {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{6,}$",message = "6자 이상, 문자와 숫자를 반드시 사용해주세요.")
    private String password;
}
