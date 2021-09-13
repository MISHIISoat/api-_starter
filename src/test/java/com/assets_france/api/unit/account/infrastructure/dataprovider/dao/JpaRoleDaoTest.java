package com.assets_france.api.unit.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.domain.exception.RoleExceptionType;
import com.assets_france.api.account.infrastructure.mapper.RoleMapper;
import com.assets_france.api.account.infrastructure.dataprovider.dao.JpaRoleDao;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import com.assets_france.api.shared.domain.exception.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaRoleDaoTest {

    @Mock
    RoleRepository mockRoleRepository;

    JpaRoleDao sut;

    @BeforeEach
    void setup() {
        sut = new JpaRoleDao(mockRoleRepository, new RoleMapper());
    }

    @Test
    void when_role_with_given_role_name_exists_should_throw_forbidden_exception() {
        var roleToSave = new Role().setName("new role");
        when(mockRoleRepository.existsByName("NEW ROLE")).thenReturn(true);

        assertThatThrownBy(() -> sut.save(roleToSave))
                .isExactlyInstanceOf(ForbiddenException.class)
                .hasMessage("%s: role with name '%s' already exists",
                        RoleExceptionType.ROLE_FORBIDDEN,
                        "NEW ROLE"
                );
    }

    @Test
    void when_role_not_exists_should_save_new_role_and_return_saved_role() throws ForbiddenException {
        var roleToSave = new Role().setName("new role");
        var jpaRoleToSave = new JpaRole().setName("NEW ROLE");
        var jpaSavedRole = new JpaRole().setId(1L).setName("NEW ROLE");

        when(mockRoleRepository.existsByName("NEW ROLE")).thenReturn(false);
        when(mockRoleRepository.save(jpaRoleToSave)).thenReturn(jpaSavedRole);

        var result = sut.save(roleToSave);

        var expected = new Role().setId(1L).setName("NEW ROLE");
        assertThat(result).isEqualTo(expected);
    }
}