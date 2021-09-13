package com.carero.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class SecurityUtils {

    /**
     * Security Context의 Auth 객체를 이용하여 username을 추출해 주는 유틸성 메소드
     * @return username
     */
    public static Optional<String> getCurrentUsername(){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null){
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if(auth.getPrincipal() instanceof UserDetails){
            UserDetails springSecruityUser = (UserDetails) auth.getPrincipal();
            username = springSecruityUser.getUsername();
        } else if(auth.getPrincipal() instanceof String){
            username = (String) auth.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
