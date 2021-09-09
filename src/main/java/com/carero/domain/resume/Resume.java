package com.carero.domain.resume;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.RecruitSubCat;
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
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reusme_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumeSubCat> subCats = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "certification_info_id")
    private CertificationInfo certificationInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resume_wanted_info_id")
    private ResumeWantedInfo resumeWantedInfo;

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

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumeFile> resumeFile = new ArrayList<>();

    public void changeNation(String nation) {
        this.nation = nation;
    }

    public void changeIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public void changeInfo(CertificationInfo certificationInfo, ResumeWantedInfo resumeWantedInfo, String educationInfo,
                           String careerInfo, String detailInfo) {
        this.certificationInfo = certificationInfo;
        this.resumeWantedInfo = resumeWantedInfo;
        this.educationInfo = educationInfo;
        this.careerInfo = careerInfo;
        this.detailInfo = detailInfo;
    }

    @Builder
    public Resume(User user, List<SubCategory> subCats, CertificationInfo certificationInfo, ResumeWantedInfo resumeWantedInfo,
                  String title, String nation, String educationInfo, String careerInfo, Boolean isParent, String contactTime, String detailInfo) {
        this.user = user;
        this.certificationInfo = certificationInfo;
        this.resumeWantedInfo = resumeWantedInfo;
        this.title = title;
        this.nation = nation;
        this.educationInfo = educationInfo;
        this.careerInfo = careerInfo;
        this.isParent = isParent;
        this.contactTime = contactTime;
        this.detailInfo = detailInfo;

        this.createDate = LocalDateTime.now();
        this.modifiedDate = this.createDate;
        this.status = false;
        this.viewCount = 0;

        for (SubCategory cat : subCats) {
            ResumeSubCat resumeSubCat = ResumeSubCat.builder()
                    .resume(this)
                    .subCategory(cat)
                    .build();
            this.subCats.add(resumeSubCat);
        }

    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void updateModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public void changeContactTime(String contactTime) {
        this.contactTime = contactTime;
    }

    public void addCat(ResumeSubCat subCat) {
        this.getSubCats().add(subCat);
        subCat.connectRecruit(this);
    }

    public void addFile(ResumeFile resumeFile){
        this.resumeFile.add(resumeFile);
        resumeFile.connectResume(this);
    }
}
