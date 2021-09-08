package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.domain.mapper.RoleMapper;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JpaRoleDao implements RoleDao {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Role save(Role role) {
        var roleToSave = roleMapper.domainToEntity(role);
        var savedRole = roleRepository.save(roleToSave);
        return roleMapper.entityToDomain(savedRole);
    }
}
