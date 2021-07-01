package com.carero.service;

import com.carero.domain.Gender;
import com.carero.domain.user.User;
import com.carero.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        String pass = "12345";
        String name = "회원A";
        int age = 30;
        Gender gender = Gender.MALE;
        String email = "kkkkk@naver.com";
        String tel = "010-8888-8888";
        String city = "전라남도";
        String sigungu = "광주광역시";
        String eupmyeondong = "무슨동";

        User user = User.builder()
                .password(pass)
                .username(name)
                .age(age)
                .gender(gender)
                .email(email)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .build();

        //when
        Long userId = userService.join(user);

        //then
        Assertions.assertThat(user).isEqualTo(userService.findOne(userId));
    }

    @Test()
    public void 중복처리() throws Exception {
        //given

        //given
        String pass = "12345";
        String name = "회원A";
        int age = 30;
        Gender gender = Gender.MALE;
        String email = "kkkkk@naver.com";
        String tel = "010-8888-8888";
        String city = "전라남도";
        String sigungu = "광주광역시";
        String eupmyeondong = "무슨동";

        User userA = User.builder()
                .password(pass)
                .username(name)
                .age(age)
                .gender(gender)
                .email(email)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .build();

        User userB = User.builder()
                .password(pass)
                .username(name)
                .age(age)
                .gender(gender)
                .email(email)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .build();
        //when
        userService.join(userA);
        //then
        assertThrows(IllegalStateException.class, () ->{
            userService.join(userB);
        });
    }

}