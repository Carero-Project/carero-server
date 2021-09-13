package com.carero.dto.user;

import com.carero.domain.Gender;
import com.carero.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NonNull
    @Email
    private String username;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @NonNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{6,}$",message = "6자 이상, 문자와 숫자를 반드시 사용해주세요.")
    private String password;

    @NonNull
    private String nickname;
    @NonNull
    private int age;
    @NonNull
    private Gender gender;
    @NonNull
    private String tel;
    @NonNull
    private String city;
    @NonNull
    private String sigungu;

    public User toEntity(){

        User user = User.builder()
                .username(this.username)
                .nickname(this.nickname)
                .password(this.password)
                .age(this.age)
                .gender(this.gender)
                .tel(this.tel)
                .city(this.city)
                .sigungu(this.sigungu)
                .build();

        return user;
    }

    public UserDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.tel = user.getTel();
        this.city = user.getCity();
        this.sigungu = user.getSigungu();
    }
}
