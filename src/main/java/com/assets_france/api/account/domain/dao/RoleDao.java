package com.assets_france.api.account.domain.dao;

import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.shared.domain.exception.ForbiddenException;

public interface RoleDao {
    Role save(Role role) throws ForbiddenException;
}
