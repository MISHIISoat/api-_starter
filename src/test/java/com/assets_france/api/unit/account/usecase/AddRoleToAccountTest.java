package com.assets_france.api.unit.account.usecase;

import com.assets_france.api.account.domain.dao.AccountRoleDao;
import com.assets_france.api.account.usecase.AddRoleToAccount;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddRoleToAccountTest {
    @Mock
    AccountRoleDao mockAccountRoleDao;

    AddRoleToAccount sut;

    @BeforeEach
    void setup() {
        sut = new AddRoleToAccount(mockAccountRoleDao);
    }

    @Test
    void should_add_role_to_account_with_account_role_dao() throws NotFoundException {
        sut.execute("username", "a role");

        verify(mockAccountRoleDao, times(1))
                .addRoleToUser("username", "a role");
    }
}