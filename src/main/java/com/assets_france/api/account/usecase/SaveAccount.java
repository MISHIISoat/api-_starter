package com.assets_france.api.account.usecase;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveAccount {
    private final AccountDao accountDao;
    public Long execute(Account account) throws ForbiddenException {
        var savedAccount = accountDao.save(account);
        return savedAccount.getId();
    }
}
