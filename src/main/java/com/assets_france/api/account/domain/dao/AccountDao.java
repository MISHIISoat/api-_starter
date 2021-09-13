package com.assets_france.api.account.domain.dao;

import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountDao {
    Account save(Account user) throws ForbiddenException;

    Account findByUsername(String username) throws NotFoundException;

    Page<Account> findAllPagination(Integer page, Integer size) throws ForbiddenException;

    List<Account> findAll();
}
