package com.assets_france.api.account.infrastructure.entrypoint;

import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.infrastructure.entrypoint.request.SaveRoleRequest;
import com.assets_france.api.account.usecase.SaveRole;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.created;

@RequestMapping("/api/role")
@RestController
@RequiredArgsConstructor
public class RoleController {
    private final SaveRole saveRole;

    @PostMapping
    public ResponseEntity<Role> save(@Valid @RequestBody SaveRoleRequest request) throws ForbiddenException {
        var newRole = new Role().setName(request.getName());
        var newRoleId = saveRole.execute(newRole);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newRoleId)
                .toUri();
        return created(uri).build();
    }
}
