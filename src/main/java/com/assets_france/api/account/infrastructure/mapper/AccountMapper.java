package com.assets_france.api.account.infrastructure.mapper;

import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaAccount;
import com.assets_france.api.account.infrastructure.entrypoint.request.SaveAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final RoleMapper roleMapper;

    public Account entityToDomain(JpaAccount entity) {
        return new Account()
                .setId(entity.getId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setPassword(entity.getPassword())
                .setUsername(entity.getUsername())
                .setEmail(entity.getEmail())
                .setRoles(
                        entity.getRoles().stream()
                                .map(roleMapper::entityToDomain)
                                .collect(Collectors.toSet())
                );
    }

    public JpaAccount domainToEntity(Account domain) {
        return new JpaAccount()
                .setId(domain.getId())
                .setFirstName(domain.getFirstName())
                .setLastName(domain.getLastName())
                .setPassword(domain.getPassword())
                .setUsername(domain.getUsername())
                .setEmail(domain.getEmail())
                .setRoles(
                        domain.getRoles().stream()
                                .map(roleMapper::domainToEntity)
                                .collect(Collectors.toSet())
                );
    }

    public Account requestToDomain(SaveAccountRequest request) {
        return new Account()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setEmail(request.getEmail())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setRoles(request.getRoles());
    }
}
