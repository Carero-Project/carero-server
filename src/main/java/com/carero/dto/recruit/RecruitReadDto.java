package com.carero.dto.recruit;

import com.carero.domain.recruit.Recruit;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitReadDto {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String title;

    private String thumbnail;

    @NotEmpty
    private String username;

    private String cat;

    @NotEmpty
    private List<String> subCats = new ArrayList<>();

    // WorkInfo
    @NotEmpty
    WorkInfoDto workInfo;

    // TargetInfo
    @NotEmpty
    TargetInfoDto targetInfo;

    // WantedInfo
    @NotEmpty
    WantedInfoDto wantedInfo;

    // EtcInfo
    @NotEmpty
    EtcInfoDto etcInfo;


    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Boolean status;
    private int viewCount;

    public RecruitReadDto(Recruit recruit) {
        this.id = recruit.getId();
        this.username = recruit.getUser().getUsername();
        this.title = recruit.getTitle();
        this.workInfo = new WorkInfoDto(recruit.getWorkInfo());
        this.targetInfo = new TargetInfoDto(recruit.getTargetInfo());
        this.wantedInfo = new WantedInfoDto(recruit.getWantedInfo());
        this.etcInfo = new EtcInfoDto(recruit.getEtcInfo());
        this.createdDate = recruit.getCreatedDate();
        this.modifiedDate = recruit.getModifiedDate();
        this.status = recruit.getStatus();
        this.viewCount = recruit.getViewCount();
        this.thumbnail = null;

        recruit.getSubCats().stream().forEach(c -> c.getSubCategory().getSubCategoryName());
        this.cat = recruit.getSubCats().get(0).getSubCategory().getParentCategory().getCategoryName();
        recruit.getSubCats().stream().forEach(c -> this.subCats.add(c.getSubCategory().getSubCategoryName()));

    }

    public void attachThumbnail(String thumbFileUrl){
        this.thumbnail = thumbFileUrl;
    }

    public void attachThumbnailByLocal(String thumbFileName, String fileBaseUrl) {
        this.thumbnail = fileBaseUrl + thumbFileName;
    }
}
