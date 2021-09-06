package com.assets_france.api.api.request;

import lombok.Data;

@Data
public class AddRoleToUserRequest {
    private String username;
    private String roleName;
}
