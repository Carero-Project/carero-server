package com.carero.dto.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.*;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RecruitCreateUpdateDto {
    public RecruitCreateUpdateDto() {
    }

    @NotEmpty
    private Long userId;

    @NotEmpty
    private String title;

    @NotEmpty
    private List<SubCategoryCreateDto> cats;

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

    @Builder
    public RecruitCreateUpdateDto(@NotEmpty Long userId, @NotEmpty String title,
                                  @NotEmpty List<SubCategoryCreateDto> cats, @NotEmpty WorkInfoDto workInfo,
                                  @NotEmpty TargetInfoDto targetInfo, @NotEmpty WantedInfoDto wantedInfo,
                                  @NotEmpty EtcInfoDto etcInfo) {
        this.userId = userId;
        this.title = title;
        this.cats = cats;
        this.workInfo = workInfo;
        this.targetInfo = targetInfo;
        this.wantedInfo = wantedInfo;
        this.etcInfo = etcInfo;
    }



    public Recruit createRecruit(User user, List<SubCategory> subCats) {
        WorkInfo workInfo = this.workInfo.toEntity();

        TargetInfo targetInfo = this.targetInfo.toEntity();

        WantedInfo wantedInfo = this.wantedInfo.toEntity();

        EtcInfo etcInfo = this.etcInfo.toEntity();

        Recruit recruit = Recruit.builder()
                .user(user)
                .title(title)
                .etcInfo(etcInfo)
                .targetInfo(targetInfo)
                .wantedInfo(wantedInfo)
                .workInfo(workInfo)
                .subCats(subCats)
                .build();

        return recruit;
    }
}
