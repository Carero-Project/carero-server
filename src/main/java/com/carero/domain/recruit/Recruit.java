package com.carero.domain.recruit;

import com.carero.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Recruit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_info_id")
    private WorkInfo workInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_info_id")
    private TargetInfo targetInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wanted_info_id")
    private WantedInfo wantedInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etc_info_id")
    private EtcInfo etcInfo;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime created_date;

    @Column(nullable = false)
    private LocalDateTime modified_date;

    @Column(nullable = false)
    private LocalDateTime end_date;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, length = 11)
    private int viewCount;

}
