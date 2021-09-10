package com.assets_france.api.account.infrastructure.entrypoint;

import com.assets_france.api.account.infrastructure.entrypoint.request.AddRoleToAccountRequest;
import com.assets_france.api.account.usecase.AddRoleToAccount;
import com.assets_france.api.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.noContent;

@RequestMapping("/api/account-role")
@RestController
@RequiredArgsConstructor
public class AccountRoleController {
    private final AddRoleToAccount addRoleToAccount;

    @PostMapping
    public ResponseEntity<?> addRoleToAccount(@Valid @RequestBody AddRoleToAccountRequest request) throws NotFoundException {
        addRoleToAccount.execute(request.getUsername(), request.getRoleName());
        return noContent().build();
    }
}
