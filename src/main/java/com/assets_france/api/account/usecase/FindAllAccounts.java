package com.assets_france.api.account.usecase;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.dto.DtoListAccount;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllAccounts {
    private final AccountDao accountDao;

    public DtoListAccount execute(Integer page, Integer size) throws ForbiddenException {
        if (page == null || size == null) {
            return buildDtoListAccount();
        }
        return buildDtoListAccountWithPageParams(page, size);
    }

    private DtoListAccount buildDtoListAccount() {
        var result = new DtoListAccount();
        return result.setAccounts(accountDao.findAll());
    }

    private DtoListAccount buildDtoListAccountWithPageParams(Integer page, Integer size) throws ForbiddenException {
        var result = new DtoListAccount();
        var accountsPage = accountDao.findAllPagination(page, size);
        result.setAccounts(accountsPage.getContent());
        result.setHasNext(accountsPage.hasNext());
        result.setHasPrevious(accountsPage.hasPrevious());
        result.setTotalPages(accountsPage.getTotalPages());
        return result;
    }
}
