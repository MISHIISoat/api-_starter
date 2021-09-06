package com.assets_france.api.api;

import com.assets_france.api.api.request.AddRoleToUserRequest;
import com.assets_france.api.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/api/user-role")
@RestController
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserRequest request) {
        userRoleService.addRoleToUser(request.getUsername(), request.getRoleName());
        return ok().build();
    }
}
