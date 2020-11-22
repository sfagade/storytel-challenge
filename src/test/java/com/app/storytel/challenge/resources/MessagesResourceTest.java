/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.auth.JwtTokenProvider;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.payload.response.JwtAuthenticationResponse;
import com.app.storytel.challenge.payload.response.MessageResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author samsonfagade
 */

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessagesResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    private String accessToken;

    @BeforeAll
    void initTestAuthentication() {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "user@yahoo.com",
                        "Matthew124"
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        this.accessToken = new JwtAuthenticationResponse(jwt).getAccessToken();
    }

    /**
     * Test of fetchMessages method, of class MessagesResource.
     */
    @Test
    void testFetchDefaultMessages() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/messages")
                .header("Authorization", "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        ArrayList<MessageResponse> criteria = new ObjectMapper().reader()
                .forType(new TypeReference<ArrayList<MessageResponse>>() {})
                .readValue(result.getResponse().getContentAsString());

        assertTrue(criteria.size() > 1, "Record set greater than one");
    }

    @Test
    void testFetchMessagesWithParams() throws Exception {
        int page = 0;
        int limit = 4;
        String order = "id";
        String paramsUrl = String.format("/api/messages?page=%d&limit=%d&order=%s", page, limit, order);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(paramsUrl)
                .header("Authorization", "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        ArrayList<MessageResponse> messagesList = new ObjectMapper().reader()
                .forType(new TypeReference<ArrayList<MessageResponse>>() {})
                .readValue(result.getResponse().getContentAsString());

        assertAll(
                "Result test",
                () -> assertTrue(messagesList.size() > 1, "Checked record size greater than one"),
                () -> assertTrue(messagesList.size() < 5, "Checked right limit count"));
    }

    @Test
    void testFindMessage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/2")
                .header("Authorization", "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MessageResponse message = new ObjectMapper().reader()
                .forType(new TypeReference<MessageResponse>() {})
                .readValue(result.getResponse().getContentAsString());

        assertEquals(2L, message.getId(), "Checked right record ID");
    }

    @Test
    void testCreateNewMessage() throws Exception {
        MessageRequest messageRequest = new MessageRequest(null, "Message Subject",
                "Message very long content", 3);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .header("Authorization", "Bearer " + this.accessToken)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(messageRequest))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated()).andReturn();

        MessageResponse messageResponse = new ObjectMapper().reader()
                .forType(new TypeReference<MessageResponse>() {})
                .readValue(result.getResponse().getContentAsString());

        assertAll(
                "Message Create Tests",
                () -> assertNotNull(messageResponse.getCreated(), "Checked date-time set automatically"),
                () -> assertNotNull(messageResponse.getOwnerId(), "Checked owner set"),
                () -> assertNotNull(messageResponse.getId(), "Checked new record ID set"));
    }

    @Test
    void testUpdateExistingMessage() throws Exception {
        MessageRequest messageRequest = new MessageRequest(2L, "Message Subject updated",
                "Message very long content", 3);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/messages")
                .header("Authorization", "Bearer " + this.accessToken)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(messageRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        MessageResponse messageResponse = new ObjectMapper().reader()
                .forType(new TypeReference<MessageResponse>() {})
                .readValue(result.getResponse().getContentAsString());

        assertAll(
                "Message Update Tests",
                () -> assertNotEquals(messageResponse.getCreated(), messageResponse.getModified(),
                        "Checked created and modified different"),
                () -> assertEquals(messageResponse.getSubject(), messageRequest.getSubject(),
                        "Checked subject updated"));
    }

    @Test
    void testUpdateDifferentOwnerMessage() throws Exception {
        MessageRequest messageRequest = new MessageRequest(3L, "Message Subject updated",
                "Message very long content", 3);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/messages")
                .header("Authorization", "Bearer " + this.accessToken)
                .content(String.valueOf(messageRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/messages/1")
                .header("Authorization", "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void testDifferentOwnerDeleteMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/messages/4")
                .header("Authorization", "Bearer " + this.accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
