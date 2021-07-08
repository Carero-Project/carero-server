package com.carero.api.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.user.User;
import com.carero.dto.ResultPaging;
import com.carero.dto.recruit.RecruitReadDto;
import com.carero.dto.recruit.RecruitCreateUpdateDto;
import com.carero.dto.recruit.RecruitCUDResponseDto;
import com.carero.dto.recruit.SubCategoryCreateDto;
import com.carero.service.RecruitService;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RecruitApiController {

    private final RecruitService recruitService;
    private final UserService userService;
    private final SubCatService subCatService;


    @GetMapping("/recruits")
    public ResultPaging<RecruitReadDto> readRecruits(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "8") int limit
    ){
        List<RecruitReadDto> recruits = recruitService.findAllWithCats(page*8, limit);
        int count = recruitService.countAll();

        return new ResultPaging(count, page, recruits);
    }

    @PutMapping("/recruits/{id}")
    public RecruitCUDResponseDto updateRecruit(
            @RequestBody RecruitCreateUpdateDto recruitCreateUpdateDto,
            @PathVariable("id") Long id
            ){

        User user = userService.findOne(recruitCreateUpdateDto.getUserId());

        List<SubCategoryCreateDto> catIds = recruitCreateUpdateDto.getCats();
        List<SubCategory> subCats = catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());

        Recruit recruit = recruitCreateUpdateDto.toEntity(user,subCats);
        recruitService.update(id, recruit);

        return new RecruitCUDResponseDto(id);
    }

    @DeleteMapping("/recruits/{id}")
    public ResponseEntity<RecruitCUDResponseDto> deleteRecruit(
            @PathVariable("id") Long id){
        recruitService.deleteById(id);

        return new ResponseEntity<>(new RecruitCUDResponseDto(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("/recruits")
    public ResponseEntity<RecruitCUDResponseDto> createRecruit(@RequestBody RecruitCreateUpdateDto recruitCreateUpdateDto){
        User user = userService.findOne(recruitCreateUpdateDto.getUserId());
        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategoryCreateDto> catIds = recruitCreateUpdateDto.getCats();
        List<SubCategory> subCats = catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());

        Recruit recruit = recruitCreateUpdateDto.toEntity(user,subCats);
        Long id = recruitService.create(recruit);

        return new ResponseEntity<>(new RecruitCUDResponseDto(id), HttpStatus.CREATED);
    }
}
