package com.assets_france.api.unit.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.infrastructure.dataprovider.dao.JpaAccountRoleDao;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaAccount;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.dataprovider.repository.AccountRepository;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaAccountRoleDaoTest {

    private final String username = "concerned_username";
    private final String roleName = "concerned_role";
    @Mock
    AccountRepository mockAccountRepository;

    @Mock
    RoleRepository mockRoleRepository;

    JpaAccountRoleDao sut;

    @BeforeEach
    void setup() {
        sut = new JpaAccountRoleDao(mockAccountRepository, mockRoleRepository);
    }

    @Test
    void when_found_account_and_role_should_add_role_to_account() throws NotFoundException {
        var account = new JpaAccount()
                .setId(7L)
                .setFirstName("john")
                .setLastName("doe")
                .setUsername("john doe")
                .setPassword("john_doe_password")
                .setRoles(new HashSet<>())
                .setEmail("john@doe.com");
        when(mockAccountRepository.findByUsername(username)).thenReturn(Optional.of(account));
        var role = new JpaRole().setId(96L).setName("current role");
        when(mockRoleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        sut.addRoleToUser(username, roleName);

        assertThat(account.getRoles()).isNotNull();
        assertThat(account.getRoles()).isEqualTo(Set.of(role));
    }

    @Test
    void when_account_not_found_should_throw_exception() {
        when(mockAccountRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.addRoleToUser(username, roleName))
                .isExactlyInstanceOf(NotFoundException.class)
                .hasMessage("ACCOUNT_NOT_FOUND: Username '%s' not found", username);
    }

    @Test
    void when_role_not_found_should_throw_exception() {
        var account = new JpaAccount()
                .setId(7L)
                .setFirstName("john")
                .setLastName("doe")
                .setUsername("john doe")
                .setPassword("john_doe_password")
                .setRoles(new HashSet<>())
                .setEmail("john@doe.com");
        when(mockAccountRepository.findByUsername(username)).thenReturn(Optional.of(account));
        when(mockRoleRepository.findByName(roleName)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.addRoleToUser(username, roleName))
                .isExactlyInstanceOf(NotFoundException.class)
                .hasMessage("ROLE_NOT_FOUND: Role name '%s' not found", roleName);
    }
}