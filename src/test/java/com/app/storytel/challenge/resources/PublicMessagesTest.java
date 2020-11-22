package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.payload.response.MessageResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PublicMessagesTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test of fetchMessages method, of class MessagesResource.
     */
    @Test
    void testFetchDefaultMessages() throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/public")
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
        String paramsUrl = String.format("/api/public?page=%d&limit=%d&order=%s", page, limit, order);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get(paramsUrl)
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
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/public/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MessageResponse message = new ObjectMapper().reader()
                .forType(new TypeReference<MessageResponse>() {})
                .readValue(result.getResponse().getContentAsString());

        assertEquals(2L, message.getId(), "Checked right record ID");
    }
}