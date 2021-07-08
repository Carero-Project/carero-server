package com.carero.domain.resume;

import com.carero.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Resume {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reusme_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private String nation;

    private String educationInfo;

    private String careerInfo;

    private Boolean isParent;

    private String contactTime;

    @Column(columnDefinition = "TEXT")
    private String detailInfo;

}
