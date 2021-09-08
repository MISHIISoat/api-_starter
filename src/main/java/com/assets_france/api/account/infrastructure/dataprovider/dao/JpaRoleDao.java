package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.domain.mapper.RoleMapper;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
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
    private final RoleMapper roleMapper;

    @Transactional
    @Override
    public Role save(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        var roleToSave = roleMapper.domainToEntity(role);
        var savedRole = roleRepository.save(roleToSave);
        return roleMapper.entityToDomain(savedRole);
    }
}
