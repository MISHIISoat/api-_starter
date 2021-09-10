package com.assets_france.api.account.infrastructure.entrypoint.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AddRoleToAccountRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String roleName;
}
