package com.thangle.api.auth;

import com.thangle.domain.auth.JWTTokenService;
import com.thangle.domain.auth.JWTUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.thangle.api.auth.LoginDTOMapper.toAuthentication;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final JWTTokenService jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public JWTTokenResponseDTO login(@RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return JWTTokenResponseDTO.builder()
                .token(jwtTokenUtil.generateToken((JWTUserDetails) authentication.getPrincipal()))
                .build();
    }
}
