package com.assets_france.api.account.domain.adapter;

import com.assets_france.api.account.domain.dto.DtoAccount;
import com.assets_france.api.account.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapter {
    public DtoAccount domainToDto(Account domain) {
        return new DtoAccount()
                .setId(domain.getId())
                .setFirstName(domain.getFirstName())
                .setLastName(domain.getLastName())
                .setUsername(domain.getUsername())
                .setEmail(domain.getEmail())
                .setRoles(domain.getRoles());
    }
}
