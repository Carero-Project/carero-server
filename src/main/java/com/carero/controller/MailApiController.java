package com.carero.controller;

import com.carero.advice.exception.MailAuthKeyNotEqualException;
import com.carero.advice.exception.SessionTimeoutException;
import com.carero.dto.MailDto;
import com.carero.dto.response.RestResponse;
import com.carero.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mails")
public class MailApiController {

    private final ResponseService responseService;
    private final JavaMailSender mailSender;
    private static final String AUTH_NUM = "AUTH_NUM";

    @PostMapping("/send")
    public RestResponse sendMail(@Valid @RequestBody MailDto mailDto, HttpServletRequest request) throws MessagingException {

        HttpSession authSession = request.getSession();

        String authKey = UUID.randomUUID().toString();
        authKey = authKey.substring(0, 8);

        authSession.setAttribute(AUTH_NUM, authKey);
        // 세션 유지 시간 : 60초
        authSession.setMaxInactiveInterval(60);

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        messageHelper.setSubject("[케어로] 회원가입 인증번호");
        messageHelper.setText("케어로 회원가입 인증번호 : "+authKey);
        messageHelper.setTo(mailDto.getEmail());

        msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mailDto.getEmail()));
        mailSender.send(msg);

        return responseService.getSuccessResponse();
    }

    @GetMapping("/check")
    public RestResponse checkAuth(@RequestParam(value = "auth") String myAuthKey,
                            @SessionAttribute(name = AUTH_NUM, required = false) String authKey){

        log.info("실제 키 = {}",authKey);
        log.info("내가 보낸 키 = {}",myAuthKey);
        if(authKey == null){
            throw new SessionTimeoutException();
        }

        if(authKey.equals(myAuthKey)){
            return responseService.getSuccessResponse();
        }else{
            throw new MailAuthKeyNotEqualException();
        }

    }
}
