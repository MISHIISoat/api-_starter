package com.assets_france.api.unit.account.usecase;

import com.assets_france.api.account.domain.dao.RoleDao;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.usecase.SaveRole;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveRoleTest {
    SaveRole sut;

    @Mock
    RoleDao mockRoleDao;

    @BeforeEach
    void setup() {
        sut = new SaveRole(mockRoleDao);
    }

    @Test
    void when_role_saved_should_return_new_role_id() throws ForbiddenException {
        var newRole = new Role().setName("new role");
        var newRoleId = 684L;
        var savedRole = new Role().setId(newRoleId).setName("new role");

        when(mockRoleDao.save(newRole)).thenReturn(savedRole);

        var result = sut.execute(newRole);

        assertThat(result).isEqualTo(newRoleId);
    }
}