package com.assets_france.api.account.infrastructure.dataprovider.repository;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<JpaRole, Long> {
    Optional<JpaRole> findByName(String name);
}
