package com.assets_france.api.unit.account.usecase;

import com.assets_france.api.account.domain.adapter.AccountAdapter;
import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.dto.DtoListAccount;
import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.usecase.FindAllAccounts;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllAccountsTest {
    private final int numberPage = 7;
    private final int numberSize = 2;
    @Mock
    AccountDao mockAccountDao;

    FindAllAccounts sut;

    AccountAdapter accountAdapter = new AccountAdapter();

    @BeforeEach
    void setup() {
        sut = new FindAllAccounts(mockAccountDao, accountAdapter);
    }

    @Test
    void when_numberPage_and_numberSize_params_are_define_should_return_dto_list_account_with_defined_page_parameters() throws ForbiddenException {
        var account = new Account().setId(6L).setUsername("username").setFirstName("first").setLastName("lastname");
        var listAccount = List.of(account);
        var pageAccounts = new PageImpl<>(listAccount);
        when(mockAccountDao.findAllPagination(numberPage, numberSize)).thenReturn(pageAccounts);

        var result = sut.execute(numberPage, numberSize);

        var expectedDtoAccounts = listAccount.stream()
                .map(accountAdapter::domainToDto)
                .collect(Collectors.toList());
        var expected = new DtoListAccount()
                .setAccounts(expectedDtoAccounts)
                .setHasNext(false)
                .setHasPrevious(false)
                .setTotalPages(1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void when_page_null_should_not_find_all_pagination() throws ForbiddenException {
        sut.execute(null, numberSize);

        verify(mockAccountDao, never()).findAllPagination(any(), any());
    }

    @Test
    void when_page_null_should_call_find_all_of_accountDao() throws ForbiddenException {
        sut.execute(null, numberSize);

        verify(mockAccountDao, times(1)).findAll();
    }

    @Test
    void when_page_null_should_return_dto_list_account_with_not_defined_page_parameters() throws ForbiddenException {
        var account = new Account().setId(6L).setUsername("username").setFirstName("first").setLastName("lastname");
        var listAccount = List.of(account);
        when(mockAccountDao.findAll()).thenReturn(listAccount);

        var result = sut.execute(null, numberSize);

        var expectedDtoAccounts = listAccount.stream()
                .map(accountAdapter::domainToDto)
                .collect(Collectors.toList());
        var expected = new DtoListAccount().setAccounts(expectedDtoAccounts);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void when_size_null_should_not_find_all_pagination() throws ForbiddenException {
        sut.execute(numberPage, null);

        verify(mockAccountDao, never()).findAllPagination(any(), any());
    }


}