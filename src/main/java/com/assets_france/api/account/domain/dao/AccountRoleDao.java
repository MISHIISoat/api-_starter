package com.assets_france.api.account.domain.dao;

import com.assets_france.api.shared.domain.exception.NotFoundException;

public interface AccountRoleDao {
    void addRoleToUser(String username, String roleName) throws NotFoundException;
}
