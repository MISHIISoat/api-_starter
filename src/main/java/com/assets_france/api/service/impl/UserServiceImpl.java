package com.assets_france.api.service.impl;

import com.assets_france.api.domain.User;
import com.assets_france.api.repository.UserRepository;
import com.assets_france.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public List<User> findAll() {
        log.info("Fetching all users");
        var users = userRepository.findAll();

        users.forEach(user -> {
            Hibernate.initialize(user.getRoles());
        });

        log.info("users fetched");
        return users;
    }
}
