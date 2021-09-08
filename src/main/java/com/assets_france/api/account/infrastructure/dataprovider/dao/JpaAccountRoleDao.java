package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaAccount;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import com.assets_france.api.account.infrastructure.dataprovider.repository.AccountRepository;
import com.assets_france.api.account.domain.dao.AccountRoleDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JpaAccountRoleDao implements AccountRoleDao {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        JpaAccount foundUser = accountRepository.findByUsername(username).get();
        JpaRole role = roleRepository.findByName(roleName);
        foundUser.getRoles().add(role);
    }
}
