package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.domain.exception.RoleExceptionType;
import com.assets_france.api.account.infrastructure.mapper.RoleMapper;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
@RequiredArgsConstructor
public class JpaRoleDao implements RoleDao {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Role save(Role role) throws ForbiddenException {
        var roleNameToCheck = role.getName().toUpperCase(Locale.ROOT);
        if (roleRepository.existsByName(roleNameToCheck)) {
            String message = String.format(
                    "%s: role with name '%s' already exists",
                    RoleExceptionType.ROLE_FORBIDDEN,
                    roleNameToCheck
            );
            throw new ForbiddenException(message);
        }
        role.setName(roleNameToCheck);
        var roleToSave = roleMapper.domainToEntity(role);
        var savedRole = roleRepository.save(roleToSave);
        return roleMapper.entityToDomain(savedRole);
    }
}
