package com.carero.advice;

import com.carero.dto.response.RestResponse;
import com.carero.advice.exception.*;
import com.carero.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse processValidationError(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("유효하지 않은 값이 들어왔습니다. :: ");
        for (ObjectError error : ex.getAllErrors()) {
            Arrays.stream(Objects.requireNonNull(error.getArguments())).forEach(e-> {
//                String result = str.substring(str.lastIndexOf("/")+1);
                String errorPartStr = e.toString();
                String errorPartStrSubstring = errorPartStr.substring(errorPartStr.indexOf("["), errorPartStr.indexOf("]")+1);

                errorMessage.append(errorPartStrSubstring);
            });
            errorMessage.append(":");
            errorMessage.append(error.getDefaultMessage());
            errorMessage.append(",");
        }
        return responseService.getFailResponse(-1000, errorMessage.toString());

    }

    @ExceptionHandler(SameBeforePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse sameBeforePasswordException(){
        return responseService.getFailResponse(-1001, "기존 비밀번호와 다르게 설정해주세요.");
    }

    @ExceptionHandler(SessionTimeoutException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse sessionTimeoutException(){
        return responseService.getFailResponse(-1002,"세션이 존재하지 않거나 시간이 만료되었습니다.");
    }

    @ExceptionHandler(MyUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse suchUserNotFoundException(){
        return responseService.getFailResponse(-1003, "현재 로그인된 정보를 가져오지 못했습니다.");
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse noSuchUserException(){
        return responseService.getFailResponse(-1004, "해당 정보의 유저를 찾을 수 없습니다.");
    }

    @ExceptionHandler(NoSuchRecruitException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse noSuchRecruitException(){
        return responseService.getFailResponse(-1005, "해당 ID의 Recruit를 찾을 수 없습니다.");
    }

    @ExceptionHandler(NoSuchResumeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse noSuchResumeException(){
        return responseService.getFailResponse(-1004, "해당 ID의 Resume을 찾을 수 없습니다.");
    }

    @ExceptionHandler(MailAuthKeyNotEqualException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse mailAuthKeyNotEqualException(){
        return responseService.getFailResponse(-1003, "인증번호가 일치하지 않습니다.");
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse messagingException(){
        return responseService.getFailResponse(-2000, "메일 관련 오류가 발생했습니다.");
    }



}
