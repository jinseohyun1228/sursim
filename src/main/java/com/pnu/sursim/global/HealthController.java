package com.pnu.sursim.global;

import com.pnu.sursim.global.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public CustomResponse healthTest() {
        return CustomResponse.success("It's working very well~!");
    }
}
