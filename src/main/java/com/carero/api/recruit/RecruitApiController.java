package com.carero.api.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.user.User;
import com.carero.dto.PostRecruitDto;
import com.carero.dto.SubCategoryDto;
import com.carero.service.RecruitService;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RecruitApiController {

    private final RecruitService recruitService;
    private final UserService userService;
    private final SubCatService subCatService;

    @PostMapping("/recruits")
    public Long createRecruits(@RequestBody PostRecruitDto postRecruitDto){
        User user = userService.findOne(postRecruitDto.getUserId());
        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategoryDto> catIds = postRecruitDto.getCats();
        List<SubCategory> subCats = catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());

        Recruit recruit = postRecruitDto.toEntity(user,subCats);
        return recruitService.create(recruit);
    }
}
