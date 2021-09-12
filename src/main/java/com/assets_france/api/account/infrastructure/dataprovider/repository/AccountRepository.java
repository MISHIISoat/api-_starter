package com.assets_france.api.account.infrastructure.dataprovider.repository;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<JpaAccount, Long>, PagingAndSortingRepository<JpaAccount, Long> {
    Optional<JpaAccount> findByUsername(String username);

    Boolean existsByEmail(String email);
}
