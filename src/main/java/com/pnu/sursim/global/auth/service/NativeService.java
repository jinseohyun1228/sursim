package com.pnu.sursim.global.auth.service;

import com.pnu.sursim.domain.user.dto.JoinRequest;
import com.pnu.sursim.domain.user.dto.LoginRequest;
import com.pnu.sursim.domain.user.dto.UserVo;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.auth.jwt.JWTUtil;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NativeService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public String joinUser(JoinRequest joinRequest) {
        if (userRepository.existsByEmail(joinRequest.email())) {
            throw new CustomException(ErrorCode.EMAIL_EXISTS);
        }
        User savedUser = userRepository.save(new User(joinRequest));

        return jwtUtil.createToken(new UserVo(savedUser));
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_ERROR));
        return jwtUtil.createToken(new UserVo(user));
    }

}
