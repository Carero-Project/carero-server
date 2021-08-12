package com.carero.advice;

import com.carero.dto.response.RestResponse;
import com.carero.advice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse> processValidationError(MethodArgumentNotValidException ex) {
        RestResponse restResponse = new RestResponse(false, -1000, "유효성 검사 실패 : " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameBeforePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse SameBeforePasswordException(){
        return new RestResponse(false, -1001, "기존 비밀번호와 다르게 설정해주세요.");
    }

    @ExceptionHandler(SessionTimeoutException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse SessionTimeoutException(){
        return new RestResponse(false, -1002, "세션 시간이 만료되었습니다.")
    }


}
