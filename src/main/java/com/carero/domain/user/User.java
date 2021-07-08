package com.carero.domain.user;

import com.carero.domain.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(length = 15, nullable = false)
    private String username;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String sigungu;

    @Column(nullable = false)
    private String eupmyeondong;

    @Column(nullable = false)
    private LocalDateTime joinedDate;

    @Builder
    public User(String password, String username, int age, Gender gender,
                String email, String tel, String city, String sigungu, String eupmyeondong){
        this.password = password;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.tel = tel;
        this.city = city;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.joinedDate = LocalDateTime.now();
    }
}
