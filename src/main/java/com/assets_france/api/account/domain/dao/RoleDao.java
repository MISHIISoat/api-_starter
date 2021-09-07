package com.assets_france.api.account.domain.dao;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;

public interface RoleDao {
    JpaRole save(JpaRole role);
}
