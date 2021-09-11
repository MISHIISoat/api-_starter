package com.assets_france.api.account.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DtoListAccount {
    List<DtoAccount> accounts;
    Boolean hasNext;
    Boolean hasPrevious;
    Integer totalPages;
}
