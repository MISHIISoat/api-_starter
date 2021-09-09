package com.assets_france.api.account.infrastructure.entrypoint;

import com.assets_france.api.account.domain.dao.AccountDao;
import com.assets_france.api.account.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/api/account")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountDao accountDao;

    @GetMapping
    public ResponseEntity<Set<Account>> getAccounts() {
        return ok().body(accountDao.findAll());
    }

    @PostMapping
    public ResponseEntity<Account> save(@RequestBody Account account) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
        return created(uri).body(accountDao.save(account));
    }
}
