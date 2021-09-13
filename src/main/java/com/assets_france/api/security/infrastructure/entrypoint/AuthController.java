package com.assets_france.api.security.infrastructure.entrypoint;

import com.assets_france.api.security.infrastructure.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.StringUtils.hasText;

@RequestMapping("/api/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    @GetMapping("refresh-token")
    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var token = tokenProvider.resolveToken(request);
        if (hasText(token) && tokenProvider.validateToken(token)) {
            var authentication = tokenProvider.getAuthentication(token);
            String accessToken = tokenProvider.createToken(authentication, TokenProvider.TypeToken.access);
            String refreshToken = tokenProvider.createToken(authentication, TokenProvider.TypeToken.refresh);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } else {
            throw new RuntimeException("Refresh token is not valid");
        }
    }
}
