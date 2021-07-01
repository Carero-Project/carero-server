package com.carero.service;

import com.carero.domain.user.User;
import com.carero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user){
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user){
        // DB에 limit 제약조건 추가 필요
        List<User> findUsers = userRepository.findByName(user.getUsername());
        if(!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public User findOne(Long userId){
        return userRepository.findOne(userId);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
