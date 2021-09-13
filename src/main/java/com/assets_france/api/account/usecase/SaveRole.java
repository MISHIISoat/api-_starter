package com.assets_france.api.account.usecase;

import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveRole {
    private final RoleDao roleDao;

    public Long execute(Role role) throws ForbiddenException {
        var savedRole = roleDao.save(role);
        return savedRole.getId();
    }
}
