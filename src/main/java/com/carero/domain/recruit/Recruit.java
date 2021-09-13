package com.carero.domain.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.resume.ResumeFile;
import com.carero.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Recruit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitSubCat> subCats = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL ,orphanRemoval = true)
    @JoinColumn(name = "work_info_id")
    private WorkInfo workInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL ,orphanRemoval = true)
    @JoinColumn(name = "target_info_id")
    private TargetInfo targetInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL ,orphanRemoval = true)
    @JoinColumn(name = "wanted_info_id")
    private WantedInfo wantedInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL ,orphanRemoval = true)
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

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitFile> recruitFiles = new ArrayList<>();


    public void changeInfo(WorkInfo workInfo, TargetInfo targetInfo, WantedInfo wantedInfo,EtcInfo etcInfo){
        this.workInfo = workInfo;
        this.targetInfo = targetInfo;
        this.wantedInfo = wantedInfo;
        this.etcInfo = etcInfo;
    }

    public void updateModifiedDate(){
        this.modifiedDate = LocalDateTime.now();
    }


    public void changeTitle(String title) {
        this.title = title;
    }

    public void addCat(RecruitSubCat subCat){
        this.getSubCats().add(subCat);
        subCat.connectRecruit(this);
    }

    @Builder
    public Recruit(User user, WorkInfo workInfo, TargetInfo targetInfo, WantedInfo wantedInfo,
                   EtcInfo etcInfo, String title,List<SubCategory> subCats){
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

        for(SubCategory subCat : subCats){
            RecruitSubCat recruitSubCat = RecruitSubCat.builder()
                    .recruit(this)
                    .subCategory(subCat)
                    .build();
            this.subCats.add(recruitSubCat);
        }
    }

    public void addFile(RecruitFile recruitFile) {
        this.recruitFiles.add(recruitFile);
        recruitFile.connectRecruit(this);
    }
}
