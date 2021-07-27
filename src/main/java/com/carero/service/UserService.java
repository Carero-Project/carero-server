package com.carero.service;

import com.carero.domain.user.User;
import com.carero.dto.user.UserDto;
import com.carero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(User user){
        validateDuplicateUser(user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setEncodedPassword(encodedPassword);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user){
        // DB에 limit 제약조건 추가 필요
        User findUser = userRepository.findOneByUsername(user.getUsername()).orElse(null);

        if(findUser != null){
            throw new IllegalStateException("중복된 회원이름 입니다.");
        }
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 회원은 존재하지 않습니다."));
    }

    public User findOneByName(String username){
        return userRepository.findOneByUsername(username)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 username입니다."));
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
