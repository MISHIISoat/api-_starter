package com.assets_france.api.account.infrastructure.entrypoint.request;

import com.assets_france.api.account.domain.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class SaveAccountRequest {
    private String firstName;

    private String lastName;

    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Set<Role> roles = new HashSet<>();
}
