package com.assets_france.api.account.infrastructure.dataprovider.repository;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<JpaUser, Long> {
    JpaUser findByUsername(String username);
}
