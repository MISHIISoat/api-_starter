package com.assets_france.api.unit.account.usecase;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.usecase.FindAllAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllAccountsTest {
    @Mock
    AccountDao mockAccountDao;

    FindAllAccounts sut;

    @BeforeEach
    void setup() {
        sut = new FindAllAccounts(mockAccountDao);
    }

    @Test
    void should_call_find_all_of_account_dao() {
        sut.execute();
        verify(mockAccountDao, times(1)).findAll();
    }

    @Test
    void should_return_set_accounts() {
        var account = new Account().setId(6L).setUsername("username").setFirstName("first").setLastName("lastname");
        var setList = Set.of(account);
        when(mockAccountDao.findAll()).thenReturn(setList);

        var result = sut.execute();

        assertThat(result).isEqualTo(setList);
    }
}