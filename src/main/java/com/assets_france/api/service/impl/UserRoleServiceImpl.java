package com.assets_france.api.service.impl;

import com.assets_france.api.domain.Role;
import com.assets_france.api.domain.User;
import com.assets_france.api.repository.RoleRepository;
import com.assets_france.api.repository.UserRepository;
import com.assets_france.api.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User foundUser = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        foundUser.getRoles().add(role);
    }
}
