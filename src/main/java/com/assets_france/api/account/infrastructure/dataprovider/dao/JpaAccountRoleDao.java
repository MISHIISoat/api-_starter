package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.dao.AccountRoleDao;
import com.assets_france.api.account.domain.exception.AccountExceptionType;
import com.assets_france.api.account.domain.exception.RoleExceptionType;
import com.assets_france.api.account.infrastructure.dataprovider.repository.AccountRepository;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JpaAccountRoleDao implements AccountRoleDao {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    public void addRoleToUser(String username, String roleName) throws NotFoundException {
        var account = accountRepository.findByUsername(username)
                .orElseThrow(() -> getAccountNotFoundException(
                                AccountExceptionType.ACCOUNT_NOT_FOUND.value,
                                "Username",
                                username
                        )
                );
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> getAccountNotFoundException(
                        RoleExceptionType.ROLE_NOT_FOUND.value,
                        "Role name",
                        roleName
                ));

        account.getRoles().add(role);
    }

    private NotFoundException getAccountNotFoundException(
            String typeException,
            String dataName,
            String concernedData
    ) {
        var message = String.format(
                "%s: %s '%s' not found",
                typeException,
                dataName,
                concernedData
        );
        return new NotFoundException(message);
    }
}
