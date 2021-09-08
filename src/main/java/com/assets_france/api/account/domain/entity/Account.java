package com.assets_france.api.account.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Accessors(chain = true)
public class Account {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private Collection<Role> roles = new ArrayList<>();
}
