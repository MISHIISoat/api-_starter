package com.assets_france.api.integration.account.infrastructure.entrypoint;

import com.assets_france.api.account.infrastructure.entrypoint.request.AddRoleToAccountRequest;
import com.assets_france.api.account.usecase.AddRoleToAccount;
import com.assets_france.api.sender.domain.EmailSender;
import com.assets_france.api.shared.domain.exception.NotFoundException;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountRoleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmailSender emailSender;

    @Autowired
    JsonHelper jsonHelper;

    @MockBean
    AddRoleToAccount mockAddRoleToAccount;

    @Test
    void when_user_not_authenticate_should_send_not_authenticate_request() throws Exception {
        var addRoleToAccountRequest = new AddRoleToAccountRequest()
                .setUsername("username")
                .setRoleName("new role");

        mockMvc.perform(post("/api/account-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.objectToJson(addRoleToAccountRequest)))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"\t", "\n"})
    void when_request_contain_empty_username_should_send_bad_request(String emptyUsername) throws Exception {
        var addRoleToAccountRequest = new AddRoleToAccountRequest()
                .setUsername(emptyUsername)
                .setRoleName("new role");

        mockMvc.perform(post("/api/account-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.objectToJson(addRoleToAccountRequest)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"\t", "\n"})
    void when_request_contain_empty_roleName_should_send_bad_request(String emptyRoleName) throws Exception {
        var addRoleToAccountRequest = new AddRoleToAccountRequest()
                .setUsername("username")
                .setRoleName(emptyRoleName);

        mockMvc.perform(post("/api/account-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.objectToJson(addRoleToAccountRequest)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void should_call_usecase_addRoleToAccount_with_username_and_password() throws Exception {
        var addRoleToAccountRequest = new AddRoleToAccountRequest()
                .setUsername("username")
                .setRoleName("new role");

        mockMvc.perform(post("/api/account-role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToJson(addRoleToAccountRequest)));

        verify(mockAddRoleToAccount, times(1)).execute("username", "new role");
    }

    @WithMockUser
    @Test
    void when_usecase_addRoleToAccount_throw_not_found_exception_should_send_not_found_error_response() throws Exception {
        var addRoleToAccountRequest = new AddRoleToAccountRequest()
                .setUsername("username")
                .setRoleName("new role");
        doThrow(new NotFoundException("message exception")).when(mockAddRoleToAccount).execute("username", "new role");

        var errorResponse = mockMvc.perform(post("/api/account-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.objectToJson(addRoleToAccountRequest)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(errorResponse).isEqualTo("message exception");
    }

    @WithMockUser
    @Test
    void when_usecase_do_nothing_should_send_no_content_response() throws Exception {
        var addRoleToAccountRequest = new AddRoleToAccountRequest()
                .setUsername("username")
                .setRoleName("new role");

        doNothing().when(mockAddRoleToAccount).execute("username", "new role");

        mockMvc.perform(post("/api/account-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.objectToJson(addRoleToAccountRequest)))
                .andExpect(status().isNoContent());
    }
}
