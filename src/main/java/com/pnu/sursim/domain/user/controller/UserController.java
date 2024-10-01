package com.pnu.sursim.domain.user.controller;

import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.domain.user.dto.ProfileRequest;
import com.pnu.sursim.domain.user.dto.UserVo;
import com.pnu.sursim.domain.user.service.UserService;
import com.pnu.sursim.global.auth.resolver.SessionUser;
import com.pnu.sursim.global.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public CustomResponse getUserInfo(@SessionUser AuthUser user) {
        UserVo userVo = userService.getUserInfo(user);
        return CustomResponse.success(userVo);
    }


    @PatchMapping("/user/profile")
    public CustomResponse changeProfile(@SessionUser AuthUser user, @RequestBody ProfileRequest profileRequest) {
        userService.changeProfile(user, profileRequest);
        return CustomResponse.success("successfully changed your profile.");
    }

}
