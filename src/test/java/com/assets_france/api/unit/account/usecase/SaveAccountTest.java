package com.assets_france.api.unit.account.usecase;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.usecase.SaveAccount;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveAccountTest {

    @Mock
    AccountDao mockAccountDao;

    SaveAccount sut;

    @BeforeEach
    void setup() {
        sut = new SaveAccount(mockAccountDao);
    }

    @Test
    void when_account_save_should_return_new_account_id() throws ForbiddenException {
        var account = new Account().setFirstName("firstname")
                .setLastName("lastname")
                .setUsername("username")
                .setEmail("first@last.com")
                .setPassword("password")
                .setRoles(Set.of(new Role().setId(65L).setName("new role")));
        var newAccountId = 687L;
        var savedAccount = new Account()
                .setId(newAccountId)
                .setFirstName("firstname")
                .setLastName("lastname")
                .setUsername("username")
                .setEmail("first@last.com")
                .setPassword("password")
                .setRoles(Set.of(new Role().setId(65L).setName("new role")));
        when(mockAccountDao.save(account)).thenReturn(savedAccount);

        var result = sut.execute(account);

        assertThat(result).isEqualTo(newAccountId);
    }
}