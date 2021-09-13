package com.assets_france.api.security.infrastructure;

import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaAccount;
import com.assets_france.api.account.infrastructure.dataprovider.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username to load {}", username);
        JpaAccount user = null;
        try {
             user = accountRepository.findByUsername(username)
                     .orElseThrow(() ->
                             new UsernameNotFoundException(
                                     String.format("Username : %s not found", username)
                             ));
        } catch (Exception e) {
            log.error(e.toString());
            log.error(e.getMessage());
        }
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("Use found in the database : {}", user);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Hibernate.initialize(user);
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
