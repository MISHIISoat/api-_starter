package com.assets_france.api.account.domain.entity;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class Account {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private Collection<JpaRole> roles = new ArrayList<>();
}
