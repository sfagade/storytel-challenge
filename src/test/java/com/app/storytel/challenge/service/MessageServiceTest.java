package com.app.storytel.challenge.service;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.respository.ApplicationRoleRepository;
import com.app.storytel.challenge.respository.LoginInformationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    private LoginInformationRepository loginInformationRepository;
    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    private MessageService messageService;
    @Autowired
    private LoginInformationService loginInformationService;

    @BeforeEach
    void setUp() {

        ApplicationRole applicationRole = new ApplicationRole();
        applicationRole.setRoleName("User");
        applicationRoleRepository.save(applicationRole);

        /** LoginInformation loginInformation = new LoginInformation();
        loginInformation.setEmailAddress("user@test.com");
        loginInformation.setPassword("user_password");
        loginInformation.setApplicationRole(applicationRole);

        this.loginInformationRepository.save(loginInformation); */
    }

    @Test
    void saveNewMessageTest() {
        LoginInformationRequest loginInformationRequest = new LoginInformationRequest();
        loginInformationRequest.setEmailAddress("user@test.com");
        loginInformationRequest.setPassword("user_password");
        loginInformationRequest.setRoleId(1L);
        LoginInformation owner = this.loginInformationService.saveNewLoginInformation(loginInformationRequest);
        //LoginInformation owner = this.loginInformationService.findLoginInformation(1L);
        assertNotNull(owner, "Message owner empty");

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageContent("This is a very long content");
        messageRequest.setSubject("Subject of the message");

        Message testMessage = this.messageService.saveNewMessage(messageRequest, owner);
        assertNotNull(testMessage, "Save message test failed with nul");
        assertNotNull(testMessage.getId(), "Save message test failed with no ID");
    }

    @Test
    void updateMessageTest() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageContent("This is a very long content");
        messageRequest.setSubject("Subject of the message");
        messageRequest.setViews(20);
        long messageId = 1;

        boolean updatedRecord = this.messageService.updateMessage(messageId, messageRequest);
        assertTrue(updatedRecord, "update message test failed");
    }

    @Test
    void findMessageTest() {
        Long messageId = 1L;
        Message message = this.messageService.findMessage(messageId);
        assertNotNull(message, "Finding message from");
    }

    @Test
    void fetchMessagesTest() {
        List<Message> messageList = this.messageService.fetchMessages(1, 5, "subject");
        assertNotNull(messageList, "Message list is empty");
        assertTrue(messageList.size() > 0, "Result set is less than zero");
    }

    @Test
    void deleteMessageTest() {
        Long messageId = 1L;
        boolean deletedRecord = this.messageService.deleteMessage(messageId);
        assertTrue(deletedRecord, "Deleting message failed");
    }
}