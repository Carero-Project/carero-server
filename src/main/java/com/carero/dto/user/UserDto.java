package com.carero.dto.user;

import com.carero.domain.Gender;
import com.carero.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NonNull
    @Size(max=15)
    private String username;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @NonNull
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
}
