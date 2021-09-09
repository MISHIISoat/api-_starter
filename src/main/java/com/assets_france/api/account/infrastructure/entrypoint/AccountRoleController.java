package com.assets_france.api.account.infrastructure.entrypoint;

import com.assets_france.api.account.domain.dao.AccountRoleDao;
import com.assets_france.api.account.infrastructure.entrypoint.request.AddRoleToAccountRequest;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/api/account-role")
@RestController
@RequiredArgsConstructor
public class AccountRoleController {
    private final AccountRoleDao accountRoleDao;

    @PostMapping
    public ResponseEntity<?> addRoleToAccount(@RequestBody AddRoleToAccountRequest request) throws NotFoundException {
        accountRoleDao.addRoleToUser(request.getUsername(), request.getRoleName());
        return ok().build();
    }
}
