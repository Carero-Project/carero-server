package com.carero.repository;

import com.carero.domain.Gender;
import com.carero.domain.user.User;
import com.carero.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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
        String tel = "010-8888-8888";
        String city = "전라남도";
        String sigungu = "광주광역시";

        userRepository.save(User.builder()
                .password(pass)
                .username(name)
                .nickname("회원A")
                .age(age)
                .gender(gender)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getPassword()).isEqualTo(pass);
        assertThat(user.getAge()).isEqualTo(age);
        assertThat(user.getUsername()).isEqualTo(name);
        assertThat(user.getGender()).isEqualTo(gender);
        assertThat(user.getTel()).isEqualTo(tel);
        assertThat(user.getCity()).isEqualTo(city);
        assertThat(user.getSigungu()).isEqualTo(sigungu);
        System.out.println("생성날짜: " + user.getJoinedDate());
    }

}