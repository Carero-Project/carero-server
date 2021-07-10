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

        recruit.getCats().stream().forEach(c -> c.getSubCategory().getSubCategoryName());

        String catName = recruit.getCats().get(0).getSubCategory().getParentCategory().getCategoryName();
        this.cat = catName;

        recruit.getCats().stream().forEach(c -> subCats.add(c.getSubCategory().getSubCategoryName()));

    }
}
