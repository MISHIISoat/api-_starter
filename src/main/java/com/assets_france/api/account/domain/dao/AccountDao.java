package com.assets_france.api.account.domain.dao;

import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.shared.domain.exception.NotFoundException;

import java.util.List;

public interface AccountDao {
    Account save(Account user);

    Account findByUsername(String username) throws NotFoundException, NotFoundException;

    List<Account> findAll();
}
