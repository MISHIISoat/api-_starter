package com.assets_france.api.account.usecase;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllAccounts {
    private final AccountDao accountDao;

    public List<Account> execute() {
        return accountDao.findAll();
    }
}
