package com.carero.service;

import ch.qos.logback.core.spi.LifeCycle;
import com.carero.domain.Gender;
import com.carero.domain.user.User;
import com.carero.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional()
class UserServiceTest {
    @Autowired UserService userService;
    @Autowired
    UserRepository userRepository;

    Long userId;

    @Test
    @Order(1)
    public void 회원가입() throws Exception {
        //given
        String pass = "12345";
        String name = "회원가입test";
        int age = 30;
        Gender gender = Gender.MALE;
        String tel = "010-8888-8888";
        String city = "전라남도";
        String sigungu = "광주광역시";

        User user = User.builder()
                .password(pass)
                .username(name)
                .nickname("회원A")
                .age(age)
                .gender(gender)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .build();

        //when
        this.userId =  userService.signup(user);

        //then
        Assertions.assertThat(userId).isEqualTo(userService.findById(userId).getId());
    }

    @Test
    @Order(2)
    public void 중복처리() throws Exception {

        //given
        String pass = "12345";
        String name = "중복처리test";
        int age = 30;
        Gender gender = Gender.MALE;
        String tel = "010-8888-8888";
        String city = "전라남도";
        String sigungu = "광주광역시";

        User userA = User.builder()
                .password(pass)
                .username(name)
                .nickname("회원A")
                .age(age)
                .gender(gender)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .build();

        User userB = User.builder()
                .password(pass)
                .username(name)
                .nickname("회원A")
                .age(age)
                .gender(gender)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .build();
        //when
        userService.signup(userA);
        //then
        assertThrows(IllegalStateException.class, () ->{
            userService.signup(userB);
        });
    }


    @Test
    @Order(3)
    public void 유저삭제() throws Exception {
        //given

        //when
        System.out.println(userId);
        userService.delete(userId);
        //when

        //then
        assertThrows(IllegalStateException.class, () -> {
            userService.findById(userId);
        });

    }
}
