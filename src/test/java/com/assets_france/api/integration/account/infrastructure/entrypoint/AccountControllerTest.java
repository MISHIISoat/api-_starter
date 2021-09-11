package com.assets_france.api.integration.account.infrastructure.entrypoint;

import com.assets_france.api.account.domain.dto.DtoAccount;
import com.assets_france.api.account.domain.dto.DtoListAccount;
import com.assets_france.api.account.domain.entity.Role;
import com.assets_france.api.account.domain.exception.AccountExceptionType;
import com.assets_france.api.account.usecase.FindAllAccounts;
import com.assets_france.api.shared.domain.helper.JsonHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonHelper jsonHelper;

    @MockBean
    FindAllAccounts mockFindAllAcounts;

    @Nested
    @DisplayName("GET /api/account")
    class GetAllAccountsTest {

        @Test
        void when_user_not_authenticate_should_send_unauthorized_response() throws Exception {
            mockMvc.perform(get("/api/account"))
                    .andExpect(status().isUnauthorized());
        }

        @WithMockUser
        @Test
        void should_call_usecase_getAllAccounts() throws Exception {
            mockMvc.perform(get("/api/account?page=2&size=7"));

            verify(mockFindAllAcounts, times(1)).execute(2, 7);
        }

        @WithMockUser
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"-1", "notnumber", "2.3"})
        void when_page_param_not_correct_should_send_error_response(String wrongPageValue) throws Exception {
            var url = String.format("/api/account?page=%s&size=%s", wrongPageValue, 7);
            mockMvc.perform(get(url)).andExpect(status().isBadRequest());
        }

        @WithMockUser
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"-1", "0", "notnumber", "2.3"})
        void when_size_param_not_correct_should_send_error_response(String wrongSizeValue) throws Exception {
            var url = String.format("/api/account?page=%s&size=%s", 8, wrongSizeValue);
            mockMvc.perform(get(url)).andExpect(status().isBadRequest());
        }

        @WithMockUser
        @Test
        void when_params_contain_page_but_not_size_should_throw_forbidden_response() throws Exception {
            var errorResponse = mockMvc.perform(get("/api/account?page=8"))
                    .andExpect(status().isForbidden())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            var errorMessage = String.format(
                    "%s: for page and size params, must be not only one parameter defined",
                    AccountExceptionType.ACCOUNT_FORBIDDEN
            );
            assertThat(errorResponse).isEqualTo(errorMessage);
        }

        @WithMockUser
        @Test
        void when_params_contain_size_but_not_page_should_throw_forbidden_response() throws Exception {
            var errorResponse = mockMvc.perform(get("/api/account?size=8"))
                    .andExpect(status().isForbidden())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            var errorMessage = String.format(
                    "%s: for page and size params, must be not only one parameter defined",
                    AccountExceptionType.ACCOUNT_FORBIDDEN
            );
            assertThat(errorResponse).isEqualTo(errorMessage);
        }

        @WithMockUser
        @Test
        void when_request_not_contain_parameter_should_return_list_accounts() throws Exception {
            var accounts = List.of(
                    new DtoAccount()
                            .setId(7L)
                            .setFirstName("john")
                            .setLastName("doe")
                            .setUsername("johndoe")
                            .setRoles(Set.of(new Role().setId(7L).setName("role"))),
                    new DtoAccount()
                            .setId(8L)
                            .setFirstName("jonas")
                            .setLastName("deo")
                            .setUsername("jonas")
                            .setRoles(Set.of(new Role().setId(8L).setName("another role")))
            );
            var dtoListAccount = new DtoListAccount().setAccounts(accounts);
            when(mockFindAllAcounts.execute(null, null)).thenReturn(dtoListAccount);

            var response = mockMvc.perform(get("/api/account"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            var result = jsonHelper.readStringValue(response, DtoListAccount.class);
            assertThat(result).isEqualTo(dtoListAccount);
        }

        @WithMockUser
        @Test
        void when_request_contain_page_and_size_parameterss_should_return_list_accounts() throws Exception {
            var accounts = List.of(
                    new DtoAccount()
                            .setId(7L)
                            .setFirstName("john")
                            .setLastName("doe")
                            .setUsername("johndoe")
                            .setRoles(Set.of(new Role().setId(7L).setName("role"))),
                    new DtoAccount()
                            .setId(8L)
                            .setFirstName("jonas")
                            .setLastName("deo")
                            .setUsername("jonas")
                            .setRoles(Set.of(new Role().setId(8L).setName("another role")))
            );
            var dtoListAccount = new DtoListAccount().setAccounts(accounts);
            dtoListAccount.setTotalPages(1);
            dtoListAccount.setHasNext(false);
            dtoListAccount.setHasPrevious(false);

            when(mockFindAllAcounts.execute(4, 8)).thenReturn(dtoListAccount);

            var response = mockMvc.perform(get("/api/account?page=4&size=8"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            var result = jsonHelper.readStringValue(response, DtoListAccount.class);
            assertThat(result).isEqualTo(dtoListAccount);
        }
    }
}
