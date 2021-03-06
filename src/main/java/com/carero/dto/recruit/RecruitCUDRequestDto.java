package com.carero.dto.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.*;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class RecruitCUDRequestDto {

    @NotEmpty
    @NotNull
    private String title;

    @NotEmpty
    @NotNull
    private List<SubCategoryCreateDto> cats;

    // WorkInfo
    @Valid WorkInfoDto workInfo;

    // TargetInfo
    @Valid TargetInfoDto targetInfo;

    // WantedInfo
    @Valid WantedInfoDto wantedInfo;

    // EtcInfo
    @Valid EtcInfoDto etcInfo;

    @Builder
    public RecruitCUDRequestDto(@NotEmpty String title,
                                @NotEmpty List<SubCategoryCreateDto> cats, @NotEmpty WorkInfoDto workInfo,
                                @NotEmpty TargetInfoDto targetInfo, @NotEmpty WantedInfoDto wantedInfo,
                                @NotEmpty EtcInfoDto etcInfo) {
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

        return Recruit.builder()
                .user(user)
                .title(title)
                .etcInfo(etcInfo)
                .targetInfo(targetInfo)
                .wantedInfo(wantedInfo)
                .workInfo(workInfo)
                .subCats(subCats)
                .build();
    }
}
