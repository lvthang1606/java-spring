package com.thangle.api.auth;

import com.thangle.domain.auth.JWTTokenService;
import com.thangle.domain.auth.JWTUserDetails;
import com.thangle.domain.auth.JWTUserDetailsService;
import com.thangle.domain.auth.LoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.thangle.api.auth.LoginDTOMapper.toAuthentication;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final JWTTokenService jwtTokenUtil;
    private final LoginAuthenticationProvider loginAuthenticationProvider;

    @PostMapping
    public JWTTokenResponseDTO login(final @RequestBody LoginDTO loginDTO) {

        final Authentication authentication = loginAuthenticationProvider.authenticate(toAuthentication(loginDTO));

        return JWTTokenResponseDTO.builder()
                .token(jwtTokenUtil.generateToken((JWTUserDetails) authentication.getPrincipal()))
                .build();
    }
}
