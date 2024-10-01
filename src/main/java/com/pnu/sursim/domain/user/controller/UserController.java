package com.pnu.sursim.domain.user.controller;

import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.domain.user.dto.ProfileRequest;
import com.pnu.sursim.domain.user.dto.ProfileResponse;
import com.pnu.sursim.domain.user.dto.UserVo;
import com.pnu.sursim.domain.user.service.UserService;
import com.pnu.sursim.global.auth.resolver.SessionUser;
import com.pnu.sursim.global.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public CustomResponse getUserInfo(@SessionUser AuthUser user) {
        ProfileResponse profileResponse = userService.getUserInfo(user);
        return CustomResponse.success(profileResponse);
    }


    @PatchMapping("/user/profile")
    public CustomResponse changeProfile(@SessionUser AuthUser user, @RequestBody ProfileRequest profileRequest) {
        userService.changeProfile(user, profileRequest);
        return CustomResponse.success("successfully changed your profile.");
    }

    @GetMapping("/user/point")
    public CustomResponse getUserPoint(@SessionUser AuthUser user) {
        Map<String, Integer> map = new HashMap<>();
        int point = userService.getUserPoint(user);

        map.put("point", point);
        return CustomResponse.success(map);
    }

}
