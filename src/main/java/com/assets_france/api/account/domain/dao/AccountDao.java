package com.assets_france.api.account.domain.dao;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaUser;

import java.util.List;

public interface AccountDao {
    JpaUser save(JpaUser user);

    JpaUser findByUsername(String username);

    List<JpaUser> findAll();
}
