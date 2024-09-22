package com.pnu.sursim.domain.user.controller;

import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class InfoController {

    @GetMapping("/gender")
    public ResponseEntity<?> offerGenderInfo() {
        Map<String, Object> genderMap = EnumSet.allOf(Gender.class)
                .stream()
                .collect(Collectors.toMap(
                        Gender::name,
                        gender -> new String[]{gender.getKoreanName(), gender.name()}
                ));

        return ResponseEntity.ok(genderMap);

    }

    @GetMapping("/region")
    public ResponseEntity<?> offerRegionInfo() {
        Map<String, Object> regionMap = EnumSet.allOf(Region.class)
                .stream()
                .collect(Collectors.toMap(
                        Region::name,
                        region -> new String[]{region.getKoreanName(), region.name()}
                ));
        return ResponseEntity.ok(regionMap);
    }

}
