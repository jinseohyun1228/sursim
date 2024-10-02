package com.pnu.sursim.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.Region;
import com.pnu.sursim.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserVo {

    private long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthDate;
    private Gender gender;

    public UserVo(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.birthDate = user.getBirthDate();
        this.gender = user.getGender();
    }

}
