package com.assets_france.api.unit.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.domain.exception.AccountExceptionType;
import com.assets_france.api.account.infrastructure.dataprovider.mapper.AccountMapper;
import com.assets_france.api.account.infrastructure.dataprovider.mapper.RoleMapper;
import com.assets_france.api.account.infrastructure.dataprovider.dao.JpaAccountDao;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaAccount;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.dataprovider.repository.AccountRepository;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaAccountDaoTest {

    @Mock
    AccountRepository mockAccountRepository;

    @Mock
    PasswordEncoder mockPasswordEncoder;

    AccountMapper accountMapper = new AccountMapper(new RoleMapper());

    JpaAccountDao sut;

    @BeforeEach
    void setup() {
        sut = new JpaAccountDao(mockAccountRepository, mockPasswordEncoder, accountMapper);
    }

    @DisplayName("save method")
    @Nested
    class SaveTest {

        @Test
        void should_return_saved_account() {
            var concernedPassword = "the_password";
            var encodePassword = "encode_password";
            var accountToSave = new Account()
                    .setFirstName("toto")
                    .setLastName("tata")
                    .setRoles(Set.of(new Role().setId(7L).setName("role")))
                    .setPassword(concernedPassword)
                    .setUsername("username")
                    .setEmail("toto@email.com");
            var entityAccountToSave = accountMapper.domainToEntity(accountToSave);
            entityAccountToSave.setPassword(encodePassword);
            when(mockPasswordEncoder.encode(concernedPassword)).thenReturn(encodePassword);
            var savedAccount = new JpaAccount()
                    .setFirstName("toto")
                    .setLastName("tata")
                    .setRoles(Set.of(new JpaRole().setId(7L).setName("role")))
                    .setPassword(concernedPassword)
                    .setUsername("username")
                    .setEmail("toto@email.com");
            when(mockAccountRepository.save(entityAccountToSave)).thenReturn(savedAccount);

            var result = sut.save(accountToSave);

            var expected = accountMapper.entityToDomain(savedAccount);

            assertThat(result).isEqualTo(expected);
        }
    }

    @DisplayName("findByUsername method")
    @Nested
    class FindByUsernameTest {

        @Test
        void should_return_find_account_by_username() throws NotFoundException {
            var entityRole = new JpaRole().setId(6L).setName("new role");
            var foundAccount = new JpaAccount()
                    .setId(1L)
                    .setFirstName("firstname")
                    .setLastName("lastname")
                    .setUsername("username")
                    .setEmail("username@gmail.com")
                    .setPassword("a_password")
                    .setRoles(Set.of(entityRole));
            when(mockAccountRepository.findByUsername("concernedUsername")).thenReturn(Optional.of(foundAccount));

            var expectedAccount = new Account()
                    .setId(foundAccount.getId())
                    .setFirstName(foundAccount.getFirstName())
                    .setLastName(foundAccount.getLastName())
                    .setUsername(foundAccount.getUsername())
                    .setEmail(foundAccount.getEmail())
                    .setPassword(foundAccount.getPassword())
                    .setRoles(Set.of(new Role().setId(entityRole.getId()).setName(entityRole.getName())));

            var result = sut.findByUsername("concernedUsername");

            assertThat(result).isEqualTo(expectedAccount);
        }

        @Test
        void should_throw_exception_when_account_not_found() {
            when(mockAccountRepository.findByUsername("concernedUsername")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> sut.findByUsername("concernedUsername"))
                    .isExactlyInstanceOf(NotFoundException.class)
                    .hasMessage("ACCOUNT_NOT_FOUND: username 'concernedUsername' not found");
        }
    }

    @DisplayName("findAllPagination method")
    @Nested
    class FindAllPaginationTest {

        private final int page = 3;
        private final int size = 7;

        @Test
        void when_page_param_not_defined_should_throw() {
            assertThatThrownBy(() -> sut.findAllPagination(null, size))
                    .isExactlyInstanceOf(ForbiddenException.class)
                    .hasMessage("%s: page or size must be defined", AccountExceptionType.ACCOUNT_FORBIDDEN);
        }

        @Test
        void when_size_param_not_defined_should_throw() {
            assertThatThrownBy(() -> sut.findAllPagination(page, null))
                    .isExactlyInstanceOf(ForbiddenException.class)
                    .hasMessage("%s: page or size must be defined", AccountExceptionType.ACCOUNT_FORBIDDEN);
        }

        @Test
        void when_params_are_defined_should_return_list_account_depend_to_page_accounts() throws ForbiddenException {
            var pageRequest = PageRequest.of(page, size);
            var listJpaAccounts = List.of(
                    new JpaAccount()
                            .setId(7L)
                            .setFirstName("firstname7")
                            .setLastName("lastname7")
                            .setUsername("firstlast7")
                            .setPassword("firstlast7password")
                            .setEmail("frist7@last.fr")
                            .setRoles(Set.of(new JpaRole().setId(68L).setName("new role"))),
                    new JpaAccount()
                            .setId(8L)
                            .setFirstName("firstname8")
                            .setLastName("lastname8")
                            .setUsername("firstlast8")
                            .setPassword("firstlast8password")
                            .setEmail("frist8@last.fr")
                            .setRoles(Set.of(new JpaRole().setId(687L).setName("the role")))
            );
            var pageAccounts = new PageImpl<>(listJpaAccounts);
            when(mockAccountRepository.findAll(pageRequest)).thenReturn(pageAccounts);

            var result = sut.findAllPagination(page, size);

            var expectedDomainList = listJpaAccounts.stream()
                    .map(accountMapper::entityToDomain)
                    .collect(Collectors.toList());
            var expected = new PageImpl<>(expectedDomainList);
            assertThat(result).isEqualTo(expected);
        }
    }

    @DisplayName("findAll method")
    @Nested
    class FindAllTest {
        @Test
        void should_call_findAll_of_accountRepository() {
            sut.findAll();

            verify(mockAccountRepository, times(1)).findAll();
        }

        @Test
        void when_find_all_accounts_should_return_appropriated_list() {
            var listJpaAccounts = List.of(
                    new JpaAccount()
                            .setId(7L)
                            .setFirstName("firstname7")
                            .setLastName("lastname7")
                            .setUsername("firstlast7")
                            .setPassword("firstlast7password")
                            .setEmail("frist7@last.fr")
                            .setRoles(Set.of(new JpaRole().setId(68L).setName("new role"))),
                    new JpaAccount()
                            .setId(8L)
                            .setFirstName("firstname8")
                            .setLastName("lastname8")
                            .setUsername("firstlast8")
                            .setPassword("firstlast8password")
                            .setEmail("frist8@last.fr")
                            .setRoles(Set.of(new JpaRole().setId(687L).setName("the role")))
            );
            when(mockAccountRepository.findAll()).thenReturn(listJpaAccounts);

            var result = sut.findAll();

            var expected = listJpaAccounts.stream()
                    .map(accountMapper::entityToDomain)
                    .collect(Collectors.toList());
            assertThat(result).isEqualTo(expected);
        }
    }
}