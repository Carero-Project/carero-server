package com.carero.controller;

import com.carero.dto.MailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@AutoConfigureMockMvc
@TestConstructor(autowireMode = AutowireMode.ALL)
@SpringBootTest
@RequiredArgsConstructor
class MailApiControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Test
    public void randomUuid() {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
    }

    @Test
    public void sendMailAndCheckTest() throws Exception {
        //given
        MailDto mailDto = new MailDto();
        mailDto.setEmail("kbsat@naver.com");

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/mails/send")
                .content(objectMapper.writeValueAsString(mailDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        MvcResult done = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Done"))
                .andReturn();

        MockHttpServletRequest request = done.getRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            Assertions.fail();
        }
        String auth_num = (String) session.getAttribute("AUTH_NUM");
        System.out.println(auth_num);

        //then
        requestBuilder = MockMvcRequestBuilders.get("/mails/check")
                .param("auth",auth_num)
                .sessionAttr("AUTH_NUM",auth_num);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Done"))
                .andReturn();

    }

}