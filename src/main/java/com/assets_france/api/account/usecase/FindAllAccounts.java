package com.assets_france.api.account.usecase;

import com.assets_france.api.account.domain.adapter.AccountAdapter;
import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.dto.DtoListAccount;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllAccounts {
    private final AccountDao accountDao;
    private final AccountAdapter accountAdapter;

    public DtoListAccount execute(Integer page, Integer size) throws ForbiddenException {
        if (page == null || size == null) {
            return buildDtoListAccount();
        }
        return buildDtoListAccountWithPageParams(page, size);
    }

    private DtoListAccount buildDtoListAccount() {
        var result = new DtoListAccount();
        var dtoAccounts = accountDao.findAll().stream()
                .map(accountAdapter::domainToDto)
                .collect(Collectors.toList());
        return result.setAccounts(dtoAccounts);
    }

    private DtoListAccount buildDtoListAccountWithPageParams(Integer page, Integer size) throws ForbiddenException {
        var result = new DtoListAccount();
        var accountsPage = accountDao.findAllPagination(page, size);
        var dtoAccounts = accountsPage.getContent().stream()
                .map(accountAdapter::domainToDto)
                .collect(Collectors.toList());
        result.setAccounts(dtoAccounts);
        result.setHasNext(accountsPage.hasNext());
        result.setHasPrevious(accountsPage.hasPrevious());
        result.setTotalPages(accountsPage.getTotalPages());
        return result;
    }
}
