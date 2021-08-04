package com.carero.controller;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.user.User;
import com.carero.dto.ResultPaging;
import com.carero.dto.recruit.RecruitPageDto;
import com.carero.dto.recruit.RecruitReadDto;
import com.carero.dto.recruit.RecruitCUDRequestDto;
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
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        Recruit recruit = recruitService.findById(id);
        return new RecruitReadDto(recruit);
    }

    @ApiOperation(value = "채용공고 페이지 조회", notes = "한 페이지 단위의 채용공고를 조회한다.")
    @GetMapping
    public ResultPaging<RecruitPageDto> readRecruits(
            @ApiParam(value = "조회할 페이지") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "한 페이지 당 보여질 개수")@RequestParam(value = "limit", defaultValue = "8") int limit
    ){
        List<RecruitPageDto> recruits = recruitService.findByPage(page*8, limit);
        long count = recruitService.countAll();

        return new ResultPaging(count, page, recruits);
    }

    @ApiOperation(value = "채용공고 수정", notes = "채용공고를 수정한다.")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public RecruitCUDResponseDto updateRecruit(
            @RequestBody RecruitCUDRequestDto recruitCUDRequestDto,
            @PathVariable("id") Long id
            ){

        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));
        Recruit nowRecruit = recruitService.findById(id);

        if (nowRecruit.getUser() == user) {
            List<SubCategory> subCats = getSubCategories(recruitCUDRequestDto);

            Recruit newRecruit = recruitCUDRequestDto.createRecruit(user,subCats);
            recruitService.update(id, newRecruit);
        }else{
            throw new AuthorizationServiceException("해당 글을 수정할 권한이 없습니다.");
        }

        return new RecruitCUDResponseDto(id);
    }

    @ApiOperation(value = "채용공고 삭제", notes = "채용공고를 삭제한다.")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<RecruitCUDResponseDto> deleteRecruit(
            @PathVariable("id") Long id){

        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));
        Recruit target = recruitService.findById(id);

        if (target.getUser() == user ){
            recruitService.deleteById(id);
        } else{
            throw new AuthorizationServiceException("해당 글을 삭제할 권한이 없습니다.");
        }

        return new ResponseEntity<>(new RecruitCUDResponseDto(id), HttpStatus.OK);
    }

    @ApiOperation(value = "채용공고 작성", notes = "채용공고를 작성한다.")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<RecruitCUDResponseDto> createRecruit(@RequestBody RecruitCUDRequestDto recruitCUDRequestDto){

        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));

        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategory> subCats = getSubCategories(recruitCUDRequestDto);

        Recruit recruit = recruitCUDRequestDto.createRecruit(user,subCats);
        Long id = recruitService.create(recruit);

        return new ResponseEntity<>(new RecruitCUDResponseDto(id), HttpStatus.CREATED);
    }

    private List<SubCategory> getSubCategories(@RequestBody RecruitCUDRequestDto recruitCUDRequestDto) {
        List<SubCategoryCreateDto> catIds = recruitCUDRequestDto.getCats();
        return catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
    }
}
