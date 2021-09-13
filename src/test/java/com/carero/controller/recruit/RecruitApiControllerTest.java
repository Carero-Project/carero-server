package com.carero.controller.recruit;

import com.carero.domain.Gender;
import com.carero.domain.recruit.*;
import com.carero.domain.user.User;
import com.carero.dto.SubCategoryCreateDto;
import com.carero.dto.recruit.*;
import com.carero.repository.RecruitRepository;
import com.carero.service.RecruitService;
import com.carero.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Autowired
    RecruitRepository recruitRepository;

    @AfterAll
    public void tearDown() throws Exception {
        System.out.println(" ============== 딜리트 ================");
        recruitRepository.deleteById(recruitId);
        System.out.println(" ============== 유저딜리트 ================");
        userService.delete(testUserId);
    }

    private Long testUserId;
    private Long recruitId;

    @BeforeAll
    public void initDB() {
        String pass = "1234512312";
        String name = "test";
        String nickname = "test1";
        int age = 30;
        Gender gender = Gender.MALE;
        String tel = "010-7777-7777";
        String city = "전라남도";
        String sigungu = "test시";

        User user = User.builder()
                .password(pass)
                .username(name)
                .nickname(nickname)
                .age(age)
                .gender(gender)
                .tel(tel)
                .city(city)
                .sigungu(sigungu)
                .build();

        testUserId = userService.signup(user);
    }

    @Test
    @DisplayName("Recruit API: Create Recruit")
    public void createRecruit() throws Exception {
        //given
        TargetInfoDto targetInfo = TargetInfoDto.builder()
                .targetAge(10)
                .carePlace(null)
                .remark("초등학생입니다. 용해초 재학중. 왜소한 체격")
                .species(null)
                .build();

        WantedInfoDto wantedInfo = WantedInfoDto.builder()
                .wantedAge("30")
                .wantedCareer("3년이상")
                .wantedEducation("대학2년제")
                .wantedNationality(null)
                .wantedGender("남")
                .build();

        WorkInfoDto workInfo = WorkInfoDto.builder()
                .workWeek("월화금")
                .workType("출퇴근")
                .workStartDate(LocalDate.now())
                .workTermDate("6개월 이상")
                .wageType("월급")
                .wage("1500000")
                .isCctv(false)
                .familyInfo("4인 가구")
                .mainInfo("아이를 돌봐주실 분 구합니다.")
                .city("전남")
                .sigungu("목포시")
                .build();

        EtcInfoDto etcInfo = EtcInfoDto.builder()
                .interviewFee("10000원")
                .isContract(false)
                .isInsurance(false)
                .submitDocument("이력서")
                .build();

        List<SubCategoryCreateDto> cats = new ArrayList<>();
        cats.add(new SubCategoryCreateDto(1L));
        cats.add(new SubCategoryCreateDto(2L));

        String title = "케어하실분 구합니다.";
        RecruitCUDRequestDto recruitPostDto = RecruitCUDRequestDto.builder()
                .title(title)
                .cats(cats)
                .workInfo(workInfo)
                .targetInfo(targetInfo)
                .wantedInfo(wantedInfo)
                .etcInfo(etcInfo)
                .build();


        String url = "http://localhost:" + port + "/recruits";

        //when
        ResponseEntity<RecruitCUDResponseDto> responseEntity = restTemplate.postForEntity(url, recruitPostDto, RecruitCUDResponseDto.class);

        //then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getId()).isGreaterThan(0L);

        List<Recruit> all = recruitService.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getEtcInfo().getInterviewFee()).isEqualTo(etcInfo.getInterviewFee());
        assertThat(all.get(0).getEtcInfo().getIsInsurance()).isEqualTo(etcInfo.getIsInsurance());

        recruitId = all.get(0).getId();


    }
}