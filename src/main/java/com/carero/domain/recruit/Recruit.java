package com.carero.domain.recruit;

import com.carero.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Recruit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "work_info_id")
    private WorkInfo workInfo;

    @OneToOne
    @JoinColumn(name = "target_info_id")
    private TargetInfo targetInfo;

    @OneToOne
    @JoinColumn(name = "wanted_info_id")
    private WantedInfo wantedInfo;

    @OneToOne
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
