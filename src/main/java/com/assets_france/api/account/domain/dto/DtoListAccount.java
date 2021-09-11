package com.assets_france.api.account.domain.dto;

import com.assets_france.api.account.domain.entity.Account;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DtoListAccount {
    List<Account> accounts;
    Boolean hasNext;
    Boolean hasPrevious;
    Integer totalPages;
}
