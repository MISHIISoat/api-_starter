package com.assets_france.api.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaUser;
import com.assets_france.api.account.infrastructure.dataprovider.repository.AccountRepository;
import com.assets_france.api.account.domain.dao.AccountDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JpaAccountDao implements AccountDao {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JpaUser save(JpaUser user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accountRepository.save(user);
    }

    @Override
    public JpaUser findByUsername(String username) {
        log.info("Fetching user {}", username);
        return accountRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public List<JpaUser> findAll() {
        log.info("Fetching all users");
        var users = accountRepository.findAll();

        users.forEach(user -> {
            Hibernate.initialize(user.getRoles());
        });

        log.info("users fetched");
        return users;
    }
}
