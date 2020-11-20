/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.service.MessageService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author samsonfagade
 */

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebMvcTest(MessagesResource.class)
public class MessagesResourceTest {

    @MockBean
    private MessageService messageService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test of fetchMessages method, of class MessagesResource.
     */
    @Test
    void testFetchMessages() throws Exception {
        System.out.println("fetchMessages");
        int page = 0;
        int limit = 4;
        String order = "";

        List<Message> messageList = new ArrayList<>();
        ApplicationRole applicationRole = new ApplicationRole(1L, "POWER USER", null, LocalDateTime.MAX, LocalDateTime.MAX);
        LoginInformation loginInformation = new LoginInformation(1L, "test@example.com", "decent-password", applicationRole, LocalDateTime.MAX, LocalDateTime.MAX);

        messageList.add(new Message(1L, "Message Subject 1", "Message content 1", 4, loginInformation, LocalDateTime.MAX, LocalDateTime.MIN));
        messageList.add(new Message(2L, "Message Subject 2", "Message content 2", 34, loginInformation, LocalDateTime.MAX, LocalDateTime.MIN));
        messageList.add(new Message(3L, "Message Subject 3", "Message content 3", 22, loginInformation, LocalDateTime.MAX, LocalDateTime.MIN));

        Mockito.when(messageService.fetchMessages(page, limit, order)).thenReturn(messageList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(3)))
                .andExpect((ResultMatcher) jsonPath("$[1].subject", is("Message content 2")));
    }



}
