package com.carero.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
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
    @Column(length = 1, nullable = false)
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

}
