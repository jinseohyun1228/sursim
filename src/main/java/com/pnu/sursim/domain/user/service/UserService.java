package com.pnu.sursim.domain.user.service;

import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.domain.user.dto.ProfileRequest;
import com.pnu.sursim.domain.user.dto.ProfileResponse;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ProfileResponse getUserInfo(AuthUser authUser) {
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));
        return new ProfileResponse(user);
    }

    public void changeProfile(AuthUser authUser, ProfileRequest profileRequest) {
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

        user.updateProfile(profileRequest);
        userRepository.save(user);
    }
}
