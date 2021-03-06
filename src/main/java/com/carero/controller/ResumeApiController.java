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
@RequestMapping("/resumes")
@RestController
public class ResumeApiController {

    private final UserService userService;
    private final ResumeService resumeService;
    private final SubCatService subCatService;
    private final ResponseService responseService;

    @GetMapping("/{id}")
    public RestResponse readResume(@PathVariable("id") Long id) {
        ResumeReadDto resumeDto = resumeService.findByIdWithThumbnail(id);
//        Resume resume = resumeService.findById(id);

        return responseService.getSingleResponse(resumeDto);
    }

    @GetMapping
    public RestResponse readResumes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "8") int limit
    ) {
        List<ResumeReadDto> resumes = resumeService.findByPage(page, limit);
        long count = resumeService.countAll();
        return responseService.getPageResponse(count, page, resumes);
    }

    @PutMapping("/{id}")
    public RestResponse updateResume(
            @PathVariable("id") Long resumeId,
            @Valid @RequestPart("resume") ResumeCUDRequestDto resumeDto,
            @RequestPart(name = "thumbnail", required = false) MultipartFile thumbnail // TODO ????????? ???????????? ??????
    ) {
        User user = userService.getMyUser().orElseThrow(MyUserNotFoundException::new);
        resumeService.update(user, resumeId, resumeDto, thumbnail);

        return responseService.getSingleResponse(new ResumeCUDResponseDto(resumeId));

    }

    @DeleteMapping("/{id}")
    public RestResponse deleteResume(@PathVariable("id") Long resumeId) {
        User user = userService.getMyUser().orElseThrow(MyUserNotFoundException::new);
        resumeService.delete(user, resumeId);

        return responseService.getSingleResponse(new ResumeCUDResponseDto(resumeId));
    }

    //    ?????? ????????? + Resume ??????
    @PostMapping
    public RestResponse createResume(
            @Valid @RequestPart("resume") ResumeCUDRequestDto resumeDto,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Long result = resumeService.create(user, resumeDto, thumbnail);
        return responseService.getSingleResponse(result);
    }

    @PostMapping("/zzim/{id}")
    public RestResponse zzim(@PathVariable("id") Long resumeId) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Resume resume = resumeService.findById(zzimDto.getId());
        resumeService.zzim(user, resume);

        return responseService.getSuccessResponse();
    }

    @DeleteMapping("/zzim/{id}")
    public RestResponse deleteZzim(@PathVariable("id") Long resumeId) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Resume resume = resumeService.findById(resumeId);
        resumeService.deleteZzim(user, resume);

        return responseService.getSuccessResponse();
    }

    private List<SubCategory> getSubCategories(ResumeCUDRequestDto resumeCUDRequestDto) {
        List<SubCategoryCreateDto> catIds = resumeCUDRequestDto.getCats();
        return catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
    }

}
