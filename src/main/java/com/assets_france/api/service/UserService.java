package com.assets_france.api.service;

import com.assets_france.api.domain.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User findByUsername(String username);

    List<User> findAll();
}
