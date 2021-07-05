package com.carero.api.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.user.User;
import com.carero.dto.recruit.CreateRecruitDto;
import com.carero.dto.recruit.CreateRecruitResponseDto;
import com.carero.dto.recruit.SubCategoryDto;
import com.carero.service.RecruitService;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RecruitApiController {

    private final RecruitService recruitService;
    private final UserService userService;
    private final SubCatService subCatService;

//    @PutMapping("/recruits/{id}")
//    public CreateRecruitResponseDto updateRecruits(
//            @RequestBody CreateRecruitDto createRecruitDto,
//            @PathVariable("id") Long id
//            ){
//
//        User user = userService.findOne(createRecruitDto.getUserId());
//
//        List<SubCategoryDto> catIds = createRecruitDto.getCats();
//        List<SubCategory> subCats = catIds.stream()
//                .map(o -> subCatService.findOne(o.getId()))
//                .collect(Collectors.toList());
//
//        Recruit recruit = createRecruitDto.toEntity(user,subCats);
//        recruitService.update(id, recruit);
//        return new createRecruitDto(id);
//    }

    @DeleteMapping("/recruits/{id}")
    public String deleteRecruit(
            @PathVariable("id") Long id){
        recruitService.deleteById(id);

        return "DONE";
    }

    @PostMapping("/recruits")
    public CreateRecruitResponseDto createRecruit(@RequestBody CreateRecruitDto createRecruitDto){
        User user = userService.findOne(createRecruitDto.getUserId());
        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategoryDto> catIds = createRecruitDto.getCats();
        List<SubCategory> subCats = catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());

        Recruit recruit = createRecruitDto.toEntity(user,subCats);
        Long id = recruitService.create(recruit);

        return new CreateRecruitResponseDto(id);
    }
}
