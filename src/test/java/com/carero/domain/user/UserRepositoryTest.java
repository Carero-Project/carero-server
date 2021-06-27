package com.carero.domain.user;

import com.carero.domain.Gender;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저가입_불러오기() throws Exception {
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

        userRepository.save(User.builder()
                .password(pass)
                .username(name)
                .age(age)
                .gender(gender)
                .email(email)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getPassword()).isEqualTo(pass);
        assertThat(user.getAge()).isEqualTo(age);
        assertThat(user.getUsername()).isEqualTo(name);
        assertThat(user.getGender()).isEqualTo(gender);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getTel()).isEqualTo(tel);
        assertThat(user.getCity()).isEqualTo(city);
        assertThat(user.getSigungu()).isEqualTo(sigungu);
        assertThat(user.getEupmyeondong()).isEqualTo(eupmyeondong);
        System.out.println("생성날짜: " + user.getJoinedDate());

        LocalDateTime joinedDate = user.getJoinedDate();
        Month month = joinedDate.getMonth();
    }

}