package com.carero.service;

import com.carero.domain.Gender;
import com.carero.domain.cat.SubCategory;
import com.carero.domain.recruit.*;
import com.carero.domain.user.User;
import com.carero.repository.SubCatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RecruitServiceTest {

    @Autowired UserService userService;
    @Autowired RecruitService recruitService;
    @Autowired SubCatRepository subCatRepository;

    @Test
    public void recruit작성() throws Exception {
        //given
        User user = User.builder()
                .password("12345")
                .username("회원")
                .nickname("recruit작성test")
                .age(30)
                .gender(Gender.MALE)
                .tel("010-7666-6666")
                .city("전라남도")
                .sigungu("광주광역시")
                .build();

        userService.signup(user);
        SubCategory subCat1 = subCatRepository.findById(1L);
        SubCategory subCat2 = subCatRepository.findById(2L);

        List<SubCategory> subcats = new ArrayList<>();

        subcats.add(subCat1);
        subcats.add(subCat2);

        TargetInfo targetInfo = TargetInfo.builder()
                .targetAge(10)
                .carePlace(null)
                .remark("초등학생입니다. 용해초 재학중. 왜소한 체격")
                .species(null)
                .build();

        WantedInfo wantedInfo = WantedInfo.builder()
                .wantedAge("30")
                .wantedCareer("3년이상")
                .wantedEducation("대학2년제")
                .wantedNationality(null)
                .wantedGender("남")
                .build();

        WorkInfo workInfo = WorkInfo.builder()
                .workWeek("월화금")
                .workType("출퇴근")
                .workStartDate(LocalDate.now())
                .workTermDate("6개월 이상")
                .wageType("일급")
                .wage("50000")
                .isCctv(false)
                .familyInfo("4인 가구")
                .mainInfo("아이를 돌봐주실 분 구합니다.")
                .city("전남")
                .sigungu("목포시")
                .build();

        EtcInfo etcInfo = EtcInfo.builder()
                .interviewFee("10000원")
                .isContract(false)
                .isInsurance(false)
                .submitDocument("이력서")
                .build();

        Recruit recruit = Recruit.builder()
                .user(user)
                .title("케어하실분 구합니다.")
                .etcInfo(etcInfo)
                .targetInfo(targetInfo)
                .wantedInfo(wantedInfo)
                .workInfo(workInfo)
                .subCats(subcats)
                .build();

        Long recruitId = recruitService.create(recruit);
        //when
        Recruit findRecruit = recruitService.findById(recruitId);

        //then

        assertThat(findRecruit.getTitle()).isEqualTo(recruit.getTitle());
        assertThat(findRecruit.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(findRecruit.getEtcInfo().getInterviewFee()).isEqualTo(etcInfo.getInterviewFee());
        assertThat(findRecruit.getSubCats()).isSameAs(recruit.getSubCats());


    }

}