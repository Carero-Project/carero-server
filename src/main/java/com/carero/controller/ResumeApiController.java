package com.carero.controller;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.resume.Resume;
import com.carero.domain.user.User;
import com.carero.dto.ResultPaging;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.dto.resume.ResumeCUDResponseDto;
import com.carero.dto.resume.ResumeCUDRequestDto;
import com.carero.dto.resume.ResumePageDto;
import com.carero.dto.resume.ResumeReadDto;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import com.carero.service.ResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Resume"})
@RequestMapping("/resumes")
public class ResumeApiController {

    private final UserService userService;
    private final ResumeService resumeService;
    private final SubCatService subCatService;

    @ApiOperation(value = "이력서 상세조회", notes = "이력서 하나를 조회한다.")
    @GetMapping("/{id}")
    public ResumeReadDto readResume(
            @PathVariable("id") Long id
    ){
        Resume resume = resumeService.findById(id);
        return new ResumeReadDto(resume);
    }

    @ApiOperation(value = "이력서 페이지 조회", notes = "한 페이지 단위의 이력서를 조회한다.")
    @GetMapping
    public ResultPaging<ResumePageDto> readResumes(
            @ApiParam(value = "조회할 페이지") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "한 페이지 당 보여질 개수") @RequestParam(value = "limit", defaultValue = "8") int limit
    ){
        List<ResumePageDto> resumes = resumeService.findByPage(page, limit);
        long count = resumeService.countAll();
        return new ResultPaging(count, page, resumes);
    }


    @ApiOperation(value = "이력서 수정", notes = "이력서를 수정한다.")
    @PutMapping("/{id}")
    public ResumeCUDResponseDto updateResume(
            @PathVariable("id") Long id,
            @RequestBody ResumeCUDRequestDto resumeCUDRequestDto){

        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));
        Resume target = resumeService.findById(id);

        if( target.getUser() == user){
            List<SubCategory> subCats = getSubCategories(resumeCUDRequestDto);

            Resume newResume = resumeCUDRequestDto.createResume(user, subCats);
            resumeService.update(id, newResume);
        } else {
            throw new AuthorizationServiceException("해당 글을 수정할 권한이 없습니다.");
        }


        return new ResumeCUDResponseDto(id);

    }

    @ApiOperation(value = "이력서 삭제", notes = "이력서를 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResumeCUDResponseDto> deleteResume(
            @PathVariable("id") Long id){

        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));
        Resume target = resumeService.findById(id);

        if( target.getUser() == user){
            resumeService.deleteById(id);
        } else {
            throw new AuthorizationServiceException("해당 글을 삭제할 권한이 없습니다.");
        }


        return new ResponseEntity<>(new ResumeCUDResponseDto(id), HttpStatus.OK);
    }

    @ApiOperation(value = "이력서 작성", notes = "이력서를 작성한다.")
    @PostMapping
    public ResponseEntity<ResumeCUDResponseDto> createResume(@RequestBody ResumeCUDRequestDto resumeCUDRequestDto){

        User user = userService.getMyUser()
                .orElseThrow(() -> new UsernameNotFoundException("현재 로그인된 정보를 가져오지 못했습니다."));

        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategory> subCats = getSubCategories(resumeCUDRequestDto);

        Resume resume = resumeCUDRequestDto.createResume(user, subCats);
        Long id = resumeService.create(resume);

        return new ResponseEntity<>(new ResumeCUDResponseDto(id), HttpStatus.CREATED);
    }

    private List<SubCategory> getSubCategories(ResumeCUDRequestDto resumeCUDRequestDto) {
        List<SubCategoryCreateDto> catIds = resumeCUDRequestDto.getCats();
        return catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
    }

}
