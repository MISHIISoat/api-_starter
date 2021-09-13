package com.assets_france.api.integration.account.infrastructure.entrypoint;

import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.infrastructure.entrypoint.request.SaveRoleRequest;
import com.assets_france.api.account.usecase.SaveRole;
import com.assets_france.api.shared.domain.helper.JsonHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {
    private final String newRole = "new role";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonHelper jsonHelper;

    @MockBean
    SaveRole mockSaveRole;

    @Test
    void when_user_not_authenticated_should_send_unauthorized_error_response() throws Exception {
        var request = new SaveRoleRequest().setName(newRole);
        mockMvc.perform(post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToJson(request))
        ).andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"\n", "\t"})
    void when_request_role_name_empty_should_send_bad_request_error_response(String emptyRoleName) throws Exception {
        var request = new SaveRoleRequest().setName(emptyRoleName);
        mockMvc.perform(post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToJson(request))
        ).andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void when_request_correct_should_call_usecase_saveRole() throws Exception {
        var request = new SaveRoleRequest().setName(newRole);
        mockMvc.perform(post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToJson(request))
        );

        var expectedRole = new Role().setName(newRole);
        verify(mockSaveRole, times(1)).execute(expectedRole);
    }

    @WithMockUser
    @Test
    void when_usecase_saveRole_return_new_role_id_should_send_created_response() throws Exception {
        var request = new SaveRoleRequest().setName(newRole);
        var roleToSave = new Role().setName(newRole);
        var newRoleId = 6L;
        when(mockSaveRole.execute(roleToSave)).thenReturn(newRoleId);

        mockMvc.perform(post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToJson(request))
        ).andExpect(status().isCreated());
    }

    @WithMockUser
    @Test
    void when_new_role_saved_should_send_request_with_uri_of_new_role() throws Exception {
        var request = new SaveRoleRequest().setName(newRole);
        var roleToSave = new Role().setName(newRole);
        var newRoleId = 6L;
        when(mockSaveRole.execute(roleToSave)).thenReturn(newRoleId);

        var location = mockMvc.perform(post("/api/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.objectToJson(request))
                ).andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        var expectedUri = String.format("%s/api/role/%d", ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .build()
                .toUriString(), newRoleId);

        assertThat(location).isEqualTo(expectedUri);
    }
}