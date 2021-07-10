package com.carero.api.recruit;

import com.carero.domain.cat.SubCategory;
import com.carero.domain.resume.Resume;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.dto.resume.ResumeCUDResponseDto;
import com.carero.dto.resume.ResumeCreateDto;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import com.carero.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ResumeApiController {

//    private final ResumeService resumeService;
    private final UserService userService;
    private final ResumeService resumeService;
    private final SubCatService subCatService;

    @PostMapping("/resumes")
    public ResponseEntity<ResumeCUDResponseDto> createResume(@RequestBody ResumeCreateDto resumeCreateDto){
        User user = userService.findOne(resumeCreateDto.getUserId());
        if(user == null){
            throw new IllegalStateException("해당 이름의 유저는 없습니다.");
        }

        List<SubCategoryCreateDto> catIds = resumeCreateDto.getCats();
        List<SubCategory> subCats = catIds.stream()
                .map(o -> subCatService.findOne(o.getId()))
                .collect(Collectors.toList());

        Resume resume = resumeCreateDto.creteResume(user, subCats);
        Long id = resumeService.create(resume);

        return new ResponseEntity<>(new ResumeCUDResponseDto(id), HttpStatus.CREATED);
    }

}
