package com.hana.cheers_up.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.cheers_up.application.user.dto.UserAccountDto;
import com.hana.cheers_up.global.security.jwt.config.UserRequestMapper;
import com.hana.cheers_up.global.security.jwt.service.Token;
import com.hana.cheers_up.global.security.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserAccountDto userDto = userRequestMapper.toDto(oAuth2User);

        Token token = tokenService.generateToken(userDto.email(), "USER");
        log.info("{}", token);

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}