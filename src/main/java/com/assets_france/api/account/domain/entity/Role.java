package com.assets_france.api.account.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Role {
    private Long id;
    private String name;
}
