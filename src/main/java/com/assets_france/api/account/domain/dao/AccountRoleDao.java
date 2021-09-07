package com.assets_france.api.account.domain.dao;

public interface AccountRoleDao {
    void addRoleToUser(String username, String roleName);
}
