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
        User user = getUserOrThrow(authUser.getEmail());
        return new ProfileResponse(user);
    }

    public void changeProfile(AuthUser authUser, ProfileRequest profileRequest) {
        User user = getUserOrThrow(authUser.getEmail());
        user.updateProfile(profileRequest);
        userRepository.save(user);
    }

    public int getUserPoint(AuthUser authUser) {
        User user = getUserOrThrow(authUser.getEmail());
        return user.getPoint();
    }



    public String getUserPhoneNumber(AuthUser authUser) {
        User user = getUserOrThrow(authUser.getEmail());
        return user.getPhoneNumber();
    }

    private User getUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

    }

}
