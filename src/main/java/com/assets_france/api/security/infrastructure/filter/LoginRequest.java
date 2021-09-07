package com.assets_france.api.security.infrastructure.filter;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
