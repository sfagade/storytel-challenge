package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.payload.response.LoginInformationResponse;
import com.app.storytel.challenge.payload.response.MessageResponse;
import com.app.storytel.challenge.service.LoginInformationService;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
                .content(String.valueOf(loginInformationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        LoginInformationResponse loginInformationResponse = new ObjectMapper().reader()
                .forType(new TypeReference<MessageResponse>() {})
                .readValue(result.getResponse().getContentAsString());

        assertAll(
                "User Create Tests",
                () -> assertNotNull(loginInformationResponse.getCreated(), "Checked date-time set automatically"),
                () -> assertNotNull(loginInformationResponse.getRoleName(), "Checked user role set"),
                () -> assertNotNull(loginInformationResponse.getId(), "Checked new record ID set"));
    }
}
