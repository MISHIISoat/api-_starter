package com.assets_france.api.unit.account.infrastructure.dataprovider.dao;

import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.infrastructure.mapper.RoleMapper;
import com.assets_france.api.account.infrastructure.dataprovider.dao.JpaRoleDao;
import com.assets_france.api.account.infrastructure.dataprovider.entity.JpaRole;
import com.assets_france.api.account.infrastructure.dataprovider.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

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
    void save_should_return_saved_role() {
        var roleToSave = new Role().setName("new role");
        var jpaRoleToSave = new JpaRole().setName("new role");
        var jpaSavedRole = new JpaRole().setId(1L).setName("new role");

        when(mockRoleRepository.save(jpaRoleToSave)).thenReturn(jpaSavedRole);

        var result = sut.save(roleToSave);

        var expected = new Role().setId(1L).setName("new role");
        assertThat(result).isEqualTo(expected);
    }
}