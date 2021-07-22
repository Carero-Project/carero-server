package com.carero.api;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.user.User;
import com.carero.dto.ResultPaging;
import com.carero.dto.recruit.RecruitPageDto;
import com.carero.dto.recruit.RecruitReadDto;
import com.carero.dto.recruit.RecruitCreateUpdateDto;
import com.carero.dto.recruit.RecruitCUDResponseDto;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.service.RecruitService;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Recruit"})
@RequestMapping("/recruits")
public class RecruitApiController {

    private final RecruitService recruitService;
    private final UserService userService;
    private final SubCatService subCatService;

    @ApiOperation(value = "채용공고 상세조회", notes = "하나의 채용공고를 조회한다.")
    @GetMapping("/{id}")
    public RecruitReadDto readRecruit(
            @PathVariable("id") Long id
    ){
        Recruit recruit = recruitService.findOne(id);
        return new RecruitReadDto(recruit);
    }

    @ApiOperation(value = "채용공고 페이지 조회", notes = "한 페이지 단위의 채용공고를 조회한다.")
    @GetMapping
    public ResultPaging<RecruitPageDto> readRecruits(
            @ApiParam(value = "조회할 페이지") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "한 페이지 당 보여질 개수")@RequestParam(value = "limit", defaultValue = "8") int limit
    ){
        List<RecruitPageDto> recruits = recruitService.findByPage(page*8, limit);
        int count = recruitService.countAll();

        return new ResultPaging(count, page, recruits);
    }

    @ApiOperation(value = "채용공고 수정", notes = "채용공고를 수정한다.")
    @PutMapping("/{id}")
    public RecruitCUDResponseDto updateRecruit(
            @RequestBody RecruitCreateUpdateDto recruitCreateUpdateDto,
            @PathVariable("id") Long id
            ){

        User user = userService.findOne(recruitCreateUpdateDto.getUserId());

        List<SubCategory> subCats = getSubCategories(recruitCreateUpdateDto);

        Recruit newRecruit = recruitCreateUpdateDto.createRecruit(user,subCats);
        recruitService.update(id, newRecruit);

        return new RecruitCUDResponseDto(id);
    }

    @ApiOperation(value = "채용공고 삭제", notes = "채용공고를 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<RecruitCUDResponseDto> deleteRecruit(
            @PathVariable("id") Long id){
        recruitService.deleteById(id);

        return new ResponseEntity<>(new RecruitCUDResponseDto(id), HttpStatus.OK);
    }

    @ApiOperation(value = "채용공고 작성", notes = "채용공고를 작성한다.")
    @PostMapping
    public ResponseEntity<RecruitCUDResponseDto> createRecruit(@RequestBody RecruitCreateUpdateDto recruitCreateUpdateDto){
        User user = userService.findOne(recruitCreateUpdateDto.getUserId());
        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategory> subCats = getSubCategories(recruitCreateUpdateDto);

        Recruit recruit = recruitCreateUpdateDto.createRecruit(user,subCats);
        Long id = recruitService.create(recruit);

        return new ResponseEntity<>(new RecruitCUDResponseDto(id), HttpStatus.CREATED);
    }

    private List<SubCategory> getSubCategories(@RequestBody RecruitCreateUpdateDto recruitCreateUpdateDto) {
        List<SubCategoryCreateDto> catIds = recruitCreateUpdateDto.getCats();
        return catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
    }
}
