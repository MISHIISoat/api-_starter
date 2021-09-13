package com.assets_france.api.account.infrastructure.mapper;

import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.entrypoint.request.SaveRoleRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public Role entityToDomain(JpaRole entity) {
        return new Role()
                .setId(entity.getId())
                .setName(entity.getName());
    }

    public JpaRole domainToEntity(Role domain) {
        return new JpaRole()
                .setId(domain.getId())
                .setName(domain.getName());
    }

    public Role requestToDomain(SaveRoleRequest request) {
        return new Role().setName(request.getName());
    }
}
