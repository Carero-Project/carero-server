package com.carero.domain.recruit;

import com.carero.domain.Gender;
import com.carero.domain.cat.SubCategory;
import com.carero.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, length = 11)
    private int viewCount;

    @Builder
    public Recruit(User user, WorkInfo workInfo, TargetInfo targetInfo, WantedInfo wantedInfo,
                   EtcInfo etcInfo, String title){
        this.user = user;
        this.workInfo = workInfo;
        this.targetInfo = targetInfo;
        this.wantedInfo = wantedInfo;
        this.etcInfo = etcInfo;
        this.title = title;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = this.createdDate;
        this.status = false;
        this.viewCount = 0;

    }

}
