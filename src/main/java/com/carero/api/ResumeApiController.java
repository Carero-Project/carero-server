package com.carero.api;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.resume.Resume;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.dto.recruit.RecruitReadDto;
import com.carero.dto.resume.ResumeCUDResponseDto;
import com.carero.dto.resume.ResumeCreateDto;
import com.carero.dto.resume.ResumeReadDto;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import com.carero.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ResumeApiController {

    private final UserService userService;
    private final ResumeService resumeService;
    private final SubCatService subCatService;

    @GetMapping("/resumes/{id}")
    public ResumeReadDto readResume(
            @PathVariable("id") Long id
    ){
        Resume resume = resumeService.findById(id);
        return new ResumeReadDto(resume);
    }

    @PutMapping("/resumes/{id}")
    public ResumeCUDResponseDto updateResume(
            @PathVariable("id") Long id,
            @RequestBody ResumeCreateDto resumeCreateDto){

        User user = userService.findOne(id);

        List<SubCategory> subCats = getSubCategories(resumeCreateDto);

        Resume newResume = resumeCreateDto.createResume(user, subCats);
        resumeService.update(id, newResume);

        return new ResumeCUDResponseDto(id);

    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<ResumeCUDResponseDto> deleteResume(
            @PathVariable("id") Long id){
        resumeService.deleteById(id);

        return new ResponseEntity<>(new ResumeCUDResponseDto(id), HttpStatus.OK);
    }


    @PostMapping("/resumes")
    public ResponseEntity<ResumeCUDResponseDto> createResume(@RequestBody ResumeCreateDto resumeCreateDto){
        User user = userService.findOne(resumeCreateDto.getUserId());
        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategory> subCats = getSubCategories(resumeCreateDto);

        Resume resume = resumeCreateDto.createResume(user, subCats);
        Long id = resumeService.create(resume);

        return new ResponseEntity<>(new ResumeCUDResponseDto(id), HttpStatus.CREATED);
    }

    private List<SubCategory> getSubCategories(ResumeCreateDto resumeCreateDto) {
        List<SubCategoryCreateDto> catIds = resumeCreateDto.getCats();
        List<SubCategory> subCats = catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());
        return subCats;
    }

}
