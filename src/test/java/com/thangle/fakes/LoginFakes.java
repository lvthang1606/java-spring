package com.thangle.fakes;

import com.thangle.api.auth.LoginDTO;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class LoginFakes {

    public static LoginDTO buildLoginDTO() {
        return LoginDTO.builder()
                .username(randomAlphabetic(3, 10))
                .password(randomAlphabetic(3, 10))
                .build();
    }
}
