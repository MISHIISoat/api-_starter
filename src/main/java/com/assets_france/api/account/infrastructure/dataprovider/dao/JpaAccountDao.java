package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.domain.exception.AccountExceptionType;
import com.assets_france.api.account.domain.mapper.AccountMapper;
import com.assets_france.api.account.infrastructure.dataprovider.repository.AccountRepository;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JpaAccountDao implements AccountDao {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @Override
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        var accountToSave = accountMapper.domainToEntity(account);
        var savedAccount = accountRepository.save(accountToSave);
        return accountMapper.entityToDomain(savedAccount);
    }

    @Override
    public Account findByUsername(String username) throws NotFoundException {
        var foundAccount = accountRepository.findByUsername(username)
                .orElseThrow(() -> {
                    var message = String.format(
                            "%s: username '%s' not found",
                            AccountExceptionType.ACCOUNT_NOT_FOUND,
                            username);
                    return new NotFoundException(message);
                });
        return accountMapper.entityToDomain(foundAccount);
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::entityToDomain)
                .collect(Collectors.toList());
    }
}
