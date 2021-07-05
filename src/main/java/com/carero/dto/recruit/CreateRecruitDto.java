package com.carero.dto.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.*;
import com.carero.domain.user.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateRecruitDto {
    public CreateRecruitDto() {
    }

    @NotEmpty
    private Long userId;

    @NotEmpty
    private String title;

    @NotEmpty
    private List<SubCategoryDto> cats;

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
    public CreateRecruitDto(@NotEmpty Long userId, @NotEmpty String title,
                            @NotEmpty List<SubCategoryDto> cats, @NotEmpty WorkInfoDto workInfo,
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



    public Recruit toEntity(User user, List<SubCategory> subCats) {
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
