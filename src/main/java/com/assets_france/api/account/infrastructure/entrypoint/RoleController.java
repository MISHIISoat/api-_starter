package com.assets_france.api.account.infrastructure.entrypoint;

import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RequestMapping("/api/role")
@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleDao roleDao;

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
        return created(uri).body(roleDao.save(role));
    }
}
