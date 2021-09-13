package com.assets_france.api.account.infrastructure.entrypoint;

import com.assets_france.api.account.domain.dto.DtoListAccount;
import com.assets_france.api.account.domain.entity.Account;
import com.assets_france.api.account.domain.exception.AccountExceptionType;
import com.assets_france.api.account.infrastructure.entrypoint.request.SaveAccountRequest;
import com.assets_france.api.account.infrastructure.mapper.AccountMapper;
import com.assets_france.api.account.usecase.FindAllAccounts;
import com.assets_france.api.account.usecase.SaveAccount;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/api/account")
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final FindAllAccounts findAllAccounts;
    private final SaveAccount saveAccount;
    private final AccountMapper accountMapper;

    @GetMapping
    public ResponseEntity<DtoListAccount> getAccounts(
            @RequestParam(required = false)
            @Pattern(regexp = "^\\d+", message = "page has to be an integer")
            @Min(value = 0, message = "page must be equal or more than 0") String page,
            @RequestParam(required = false)
            @Pattern(regexp = "^\\d+", message = "size has to be an integer")
            @Min(value = 1, message = "size must be equal or more than 0") String size
    ) throws ForbiddenException {
        if (isOnlyOneParamDefinedInBoth(page, size)) {
            var message = String.format(
                    "%s: for page and size params, must be not only one parameter defined",
                    AccountExceptionType.ACCOUNT_FORBIDDEN
            );
            log.error(message);
            throw new ForbiddenException(message);
        }
        Integer pageValue = Optional.ofNullable(page).map(Integer::valueOf).orElse(null);
        Integer sizeValue = Optional.ofNullable(size).map(Integer::valueOf).orElse(null);

        return ok().body(findAllAccounts.execute(pageValue, sizeValue));
    }

    private boolean isOnlyOneParamDefinedInBoth(String page, String size) {
        return page == null && size != null || page != null && size == null;
    }

    @PostMapping
    public ResponseEntity<Account> save(@Valid @RequestBody SaveAccountRequest request) throws ForbiddenException {
        var newAccountId = saveAccount.execute(accountMapper.requestToDomain(request));
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAccountId)
                .toUri();
        return created(uri).build();
    }
}
