package com.assets_france.api.account.infrastructure.entrypoint;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaUser;
import com.assets_france.api.account.domain.dao.AccountDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/api/account")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountDao accountDao;

    @GetMapping
    public ResponseEntity<List<JpaUser>> getAccounts() {

        return ok().body(accountDao.findAll());
    }

    @PostMapping
    public ResponseEntity<JpaUser> save(@RequestBody JpaUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
        return created(uri).body(accountDao.save(user));
    }
}
