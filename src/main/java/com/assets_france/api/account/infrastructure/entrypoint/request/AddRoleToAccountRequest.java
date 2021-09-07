package com.assets_france.api.account.infrastructure.entrypoint.request;

import lombok.Data;

@Data
public class AddRoleToAccountRequest {
    private String username;
    private String roleName;
}
