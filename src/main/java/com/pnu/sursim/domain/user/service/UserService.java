package com.pnu.sursim.domain.user.service;

import com.pnu.sursim.domain.user.dto.*;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.auth.jwt.JWTUtil;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

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

    public UserVo getUserInfo(AuthUser authUser) {
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));
        return new UserVo(user);
    }

    public UserVo changeProfile(AuthUser authUser, ProfileRequest profileRequest) {
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

        user.updateProfile(profileRequest);
        User savedUser = userRepository.save(user);

        return new UserVo(savedUser);
    }
}
