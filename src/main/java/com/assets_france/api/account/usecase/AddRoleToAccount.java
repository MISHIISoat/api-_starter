package com.assets_france.api.account.usecase;

import com.assets_france.api.account.domain.dao.AccountRoleDao;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddRoleToAccount {
    private final AccountRoleDao accountRoleDao;

    public void execute(String username, String roleName) throws NotFoundException {
        accountRoleDao.addRoleToUser(username, roleName);
    }
}
