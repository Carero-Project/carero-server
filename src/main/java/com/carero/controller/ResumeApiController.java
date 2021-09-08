package com.carero.controller;

import com.carero.advice.exception.MyUserNotFoundException;
import com.carero.domain.ResumeZzim;
import com.carero.domain.cat.SubCategory;
import com.carero.domain.resume.Resume;
import com.carero.domain.user.User;
import com.carero.dto.ZzimDto;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.dto.response.RestResponse;
import com.carero.dto.resume.ResumeCUDResponseDto;
import com.carero.dto.resume.ResumeCUDRequestDto;
import com.carero.dto.resume.ResumePageDto;
import com.carero.dto.resume.ResumeReadDto;
import com.carero.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Api(tags = {"Resume"})
@RequestMapping("/resumes")
@RestController
public class ResumeApiController {

    private final UserService userService;
    private final ResumeService resumeService;
    private final SubCatService subCatService;
    private final ResponseService responseService;
    private final FileStorageService storageService;

    @ApiOperation(value = "이력서 상세조회", notes = "이력서 하나를 조회한다.")
    @GetMapping("/{id}")
    public RestResponse readResume(
            @PathVariable("id") Long id
    ) {
        Resume resume = resumeService.findById(id);

        return responseService.getSingleResponse(new ResumeReadDto(resume));
    }

    @ApiOperation(value = "이력서 페이지 조회", notes = "한 페이지 단위의 이력서를 조회한다.")
    @GetMapping
    public RestResponse readResumes(
            @ApiParam(value = "조회할 페이지") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "한 페이지 당 보여질 개수") @RequestParam(value = "limit", defaultValue = "8") int limit
    ) {
        List<ResumePageDto> resumes = resumeService.findByPage(page, limit);
        long count = resumeService.countAll();
        return responseService.getPageResponse(count, page, resumes);
    }


    @ApiOperation(value = "이력서 수정", notes = "이력서를 수정한다.")
    @PutMapping("/{id}")
    public RestResponse updateResume(
            @PathVariable("id") Long id,
            @Valid @RequestPart("resume") ResumeCUDRequestDto resumeCUDRequestDto,
            @RequestPart("thumbnail") MultipartFile thumbnail // TODO 썸네일 수정작업 필요
    ) {

        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);
        Resume target = resumeService.findById(id);

        if (target.getUser() == user) {
            List<SubCategory> subCats = getSubCategories(resumeCUDRequestDto);

            Resume newResume = resumeCUDRequestDto.createResume(user, subCats);
            resumeService.update(id, newResume);
        } else {
            throw new AuthorizationServiceException("해당 글을 수정할 권한이 없습니다.");
        }

        return responseService.getSingleResponse(new ResumeCUDResponseDto(id));

    }

    @ApiOperation(value = "이력서 삭제", notes = "이력서를 삭제한다.")
    @DeleteMapping("/{id}")
    public RestResponse deleteResume(
            @PathVariable("id") Long id) {

        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);
        Resume target = resumeService.findById(id);

        if (target.getUser() == user) {
            resumeService.deleteById(id);
        } else {
            throw new AuthorizationServiceException("해당 글을 삭제할 권한이 없습니다.");
        }

        return responseService.getSingleResponse(new ResumeCUDResponseDto(id));
    }

//    파일 업로드 + Resume 작성
    @ApiOperation(value = "이력서 작성", notes = "이력서를 작성한다.")
    @PostMapping
    public RestResponse createResume(@Valid @RequestPart("resume") ResumeCUDRequestDto resumeDto,
                                 @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail){
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Long result = resumeService.create(user, resumeDto, thumbnail);
        return responseService.getSingleResponse(result);
    }

    @PostMapping("/zzim")
    public RestResponse zzim(@RequestBody ZzimDto zzimDto) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Resume resume = resumeService.findById(zzimDto.getId());
        resumeService.zzim(user, resume);

        return responseService.getSuccessResponse();
    }

    @DeleteMapping("/zzim/{id}")
    public RestResponse deleteZzim(@PathVariable("id") Long zzimId) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        ResumeZzim target = resumeService.findZzimById(zzimId);

        if (target.getUser() == user) {
            resumeService.deleteZzim(zzimId);
        } else {
            throw new AuthorizationServiceException("해당 찜 목록을 삭제할 권한이 없습니다.");
        }

        return responseService.getSuccessResponse();
    }

    private List<SubCategory> getSubCategories(ResumeCUDRequestDto resumeCUDRequestDto) {
        List<SubCategoryCreateDto> catIds = resumeCUDRequestDto.getCats();
        return catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
    }

}
