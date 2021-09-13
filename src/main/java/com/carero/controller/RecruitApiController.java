package com.carero.controller;

import com.carero.advice.exception.MyUserNotFoundException;
import com.carero.domain.RecruitZzim;
import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.Recruit;
import com.carero.domain.user.User;
import com.carero.dto.ZzimDto;
import com.carero.dto.recruit.RecruitPageDto;
import com.carero.dto.recruit.RecruitReadDto;
import com.carero.dto.recruit.RecruitCUDRequestDto;
import com.carero.dto.recruit.RecruitCUDResponseDto;
import com.carero.dto.response.RestResponse;
import com.carero.service.RecruitService;
import com.carero.service.ResponseService;
import com.carero.service.SubCatService;
import com.carero.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Recruit"})
@RequestMapping("/recruits")
public class RecruitApiController {

    private final RecruitService recruitService;
    private final UserService userService;
    private final SubCatService subCatService;
    private final ResponseService responseService;

    @ApiOperation(value = "채용공고 상세조회", notes = "하나의 채용공고를 조회한다.")
    @GetMapping("/{id}")
    public RestResponse readRecruit(
            @PathVariable("id") Long id
    ) {
        RecruitReadDto recruitDto = recruitService.findByIdWithThumbnail(id);

        return responseService.getSingleResponse(recruitDto);
    }

    @ApiOperation(value = "채용공고 페이지 조회", notes = "한 페이지 단위의 채용공고를 조회한다.")
    @GetMapping
    public RestResponse readRecruits(
            @ApiParam(value = "조회할 페이지") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "한 페이지 당 보여질 개수") @RequestParam(value = "limit", defaultValue = "8") int limit
    ) {
        List<RecruitPageDto> recruits = recruitService.findByPage(page * 8, limit);
        long count = recruitService.countAll();

        return responseService.getPageResponse(count, page, recruits);
    }

    @ApiOperation(value = "채용공고 수정", notes = "채용공고를 수정한다.")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public RestResponse updateRecruit(
            @Valid @RequestPart("recruit") RecruitCUDRequestDto recruitDto,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @PathVariable("id") Long recruitId
    ) {
        User user = userService.getMyUser().orElseThrow(MyUserNotFoundException::new);
        recruitService.update(user, recruitId, recruitDto, thumbnail);

        return responseService.getSingleResponse(new RecruitCUDResponseDto(recruitId));
    }

    @ApiOperation(value = "채용공고 삭제", notes = "채용공고를 삭제한다.")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public RestResponse deleteRecruit(@PathVariable("id") Long id) {
        User user = userService.getMyUser().orElseThrow(MyUserNotFoundException::new);
        recruitService.delete(user, id);

        return responseService.getSingleResponse(new RecruitCUDResponseDto(id));
    }

    @ApiOperation(value = "채용공고 작성", notes = "채용공고를 작성한다.")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public RestResponse createRecruit(
            @Valid @RequestPart("recruit") RecruitCUDRequestDto recruitDto,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Long resultId = recruitService.create(user, recruitDto, thumbnail);
        return responseService.getSingleResponse(resultId);
    }

    @PostMapping("/zzim")
    public RestResponse zzim(@RequestBody ZzimDto zzimDto) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        Recruit recruit = recruitService.findById(zzimDto.getId());
        recruitService.zzim(user, recruit);

        return responseService.getSuccessResponse();
    }

    @DeleteMapping("/zzim/{id}")
    public RestResponse deleteZzim(@PathVariable("id") Long zzimId) {
        User user = userService.getMyUser()
                .orElseThrow(MyUserNotFoundException::new);

        RecruitZzim target = recruitService.findZzimById(zzimId);

        if (target.getUser() == user) {
            recruitService.deleteZzim(zzimId);
        } else {
            throw new AuthorizationServiceException("해당 찜 목록을 삭제할 권한이 없습니다.");
        }

        return responseService.getSuccessResponse();
    }

}
