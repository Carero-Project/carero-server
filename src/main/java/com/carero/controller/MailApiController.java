package com.carero.controller;

import com.carero.dto.MailDto;
import com.carero.exception.SessionTimeoutException;
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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mails")
public class MailApiController {

    private final JavaMailSender mailSender;
    private static final String AUTH_NUM = "AUTH_NUM";

    @PostMapping("/send")
    public String sendMail(@RequestBody MailDto mailDto, HttpServletRequest request){

        HttpSession authSession = request.getSession();

        String authKey = UUID.randomUUID().toString();
        authKey = authKey.substring(0, 8);

        authSession.setAttribute(AUTH_NUM, authKey);
        // 세션 유지 시간 : 60초
        authSession.setMaxInactiveInterval(60);

        try{
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
            messageHelper.setSubject("[케어로] 회원가입 인증번호");
            messageHelper.setText("케어로 회원가입 인증번호 : "+authKey);
            messageHelper.setTo(mailDto.getEmail());

            msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mailDto.getEmail()));
            mailSender.send(msg);
        }catch(MessagingException e){
            log.error("MessagingException");
            e.printStackTrace();
            return "Fail";
        }

        return "Done";
    }

    @GetMapping("/check")
    public String checkAuth(@RequestParam(value = "auth") String myAuthKey,
                            @SessionAttribute(name = AUTH_NUM, required = false) String authKey){

        log.info("실제 키 = {}",authKey);
        log.info("내가 보낸 키 = {}",myAuthKey);
        if(authKey == null){
            throw new SessionTimeoutException("세션 시간이 만료되었습니다.");
        }

        if(authKey.equals(myAuthKey)){
            return "Done";
        }else{
            return "Fail";
        }

    }
}
