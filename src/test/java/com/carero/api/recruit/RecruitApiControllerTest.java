package com.carero.api.recruit;

import com.carero.InitDB;
import com.carero.domain.Gender;
import com.carero.domain.recruit.*;
import com.carero.domain.user.User;
import com.carero.dto.RecruitPostDto;
import com.carero.dto.RecruitPostResponseDto;
import com.carero.dto.SubCategoryDto;
import com.carero.repository.RecruitRepository;
import com.carero.service.RecruitService;
import com.carero.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Rollback(false)
class RecruitApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private UserService userService;

    @AfterAll
    public void tearDown() throws Exception {
        System.out.println(" ============== 딜리트 ================");
        recruitService.deleteAll();
        System.out.println(" ============== 유저딜리트 ================");
        userService.delete(testUserId);
    }

    private Long testUserId;

    @BeforeAll
    public void initDB() {
        String pass = "1234512312";
        String name = "test";
        int age = 30;
        Gender gender = Gender.MALE;
        String email = "kkkkk@naver.com";
        String tel = "010-7777-7777";
        String city = "전라남도";
        String sigungu = "test시";
        String eupmyeondong = "무슨동";

        User user = User.builder()
                .password(pass)
                .username(name)
                .age(age)
                .gender(gender)
                .email(email)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .build();

        userService.join(user);
        testUserId = user.getId();
    }

    @Test
    @DisplayName("Recruit API: POST Recruit")
    public void createRecruit() throws Exception {
        //given
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
                .workingStartHour(LocalTime.now())
                .workingEndHour(LocalTime.now())
                .wage("일급 50000원")
                .isCctv(false)
                .familyInfo("4인 가구")
                .petInfo("없음")
                .mainInfo("아이를 돌봐주실 분 구합니다.")
                .city("전남")
                .sigungu("목포시")
                .eupmyeondong("용당동")
                .build();

        EtcInfo etcInfo = EtcInfo.builder()
                .interviewFee("10000원")
                .isContract(false)
                .isInsurance(false)
                .submitDocument("이력서")
                .build();

        List<SubCategoryDto> cats = new ArrayList<>();
        cats.add(new SubCategoryDto(1L));
        cats.add(new SubCategoryDto(2L));

        String title = "케어하실분 구합니다.";
        RecruitPostDto recruitPostDto = RecruitPostDto.builder()
                .title(title)
                .userId(testUserId)
                .cats(cats)
                .city(workInfo.getCity())
                .sigungu(workInfo.getSigungu())
                .eupmyeondong(workInfo.getEupmyeondong())
                .workWeek(workInfo.getWorkWeek())
                .workType(workInfo.getWorkType())
                .workStartDate(workInfo.getWorkStartDate())
                .workTermDate(workInfo.getWorkTermDate())
                .workingStartHour(workInfo.getWorkingStartHour())
                .workingEndHour(workInfo.getWorkingEndHour())
                .wage(workInfo.getWage())
                .isCctv(workInfo.getIsCctv())
                .familyInfo(workInfo.getFamilyInfo())
                .petInfo(workInfo.getPetInfo())
                .mainInfo(workInfo.getMainInfo())
                .targetAge(targetInfo.getTargetAge())
                .remark(targetInfo.getRemark())
                .species(targetInfo.getSpecies())
                .wantedAge(wantedInfo.getWantedAge())
                .wantedGender(wantedInfo.getWantedGender())
                .wantedCareer(wantedInfo.getWantedCareer())
                .wantedEducation(wantedInfo.getWantedEducation())
                .wantedNationality(wantedInfo.getWantedNationality())
                .prerequisite(wantedInfo.getPrerequisite())
                .preferential(wantedInfo.getPreferential())
                .isContract(etcInfo.getIsContract())
                .isInsurance(etcInfo.getIsInsurance())
                .submitDocument(etcInfo.getSubmitDocument())
                .interviewFee(etcInfo.getInterviewFee())
                .build();

        String url = "http://localhost:" + port + "/recruits";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, recruitPostDto, Long.class);

        //then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Recruit> all = recruitService.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getEtcInfo().getInterviewFee()).isEqualTo(etcInfo.getInterviewFee());
        assertThat(all.get(0).getEtcInfo().getIsInsurance()).isEqualTo(etcInfo.getIsInsurance());

    }
}