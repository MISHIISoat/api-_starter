package com.assets_france.api.security.infrastructure.filter;

import com.assets_france.api.security.infrastructure.TokenProvider;
import com.assets_france.api.shared.helper.JsonHelper;
import com.assets_france.api.shared.helper.exception.JsonHelperException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final TokenProvider tokenProvider;
    private final JsonHelper jsonHelper;

    public CustomAuthenticationFilter(
            AuthenticationManager authenticationManager,
            TokenProvider tokenProvider,
            JsonHelper jsonHelper
    ) {
        super(authenticationManager);
        super.setFilterProcessesUrl("/api/login");
        this.tokenProvider = tokenProvider;
        this.jsonHelper = jsonHelper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var loginRequest = jsonHelper.readInputStreamValue(request.getInputStream(), LoginRequest.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
        } catch (IOException | JsonHelperException e) {
            e.printStackTrace();
            throw new BadCredentialsException("Could not authenticate user");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        var accessToken = tokenProvider.createToken(authResult, TokenProvider.TypeToken.access);
        var refreshToken = tokenProvider.createToken(authResult, TokenProvider.TypeToken.refresh);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}

