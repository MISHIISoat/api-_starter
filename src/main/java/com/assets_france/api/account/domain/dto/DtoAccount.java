package com.assets_france.api.account.domain.dto;

import com.assets_france.api.account.domain.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class DtoAccount {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Set<Role> roles = new HashSet<>();
}
