package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.payload.response.LoginInformationResponse;
import com.app.storytel.challenge.payload.response.MessageResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerUser() throws Exception {

        LoginInformationRequest loginInformationRequest = new LoginInformationRequest("mulan@gmail.com", "mulan-password", 2L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/authentication/register")
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(loginInformationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        LoginInformationResponse loginInformationResponse = new ObjectMapper().reader()
                .forType(new TypeReference<LoginInformationResponse>(){})
                .readValue(result.getResponse().getContentAsString());

        assertAll(
                "User Create Tests",
                () -> assertNotNull(loginInformationResponse.getCreated(), "Checked date-time set automatically"),
                () -> assertNotNull(loginInformationResponse.getRoleName(), "Checked user role set"),
                () -> assertNotNull(loginInformationResponse.getId(), "Checked new record ID set"));
    }
}
