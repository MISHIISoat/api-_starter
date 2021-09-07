package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import com.assets_france.api.account.domain.dao.RoleDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JpaRoleDao implements RoleDao {
    private final RoleRepository roleRepository;
    @Transactional
    @Override
    public JpaRole save(JpaRole role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }
}
