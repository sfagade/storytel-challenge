package com.app.storytel.challenge.service.impl;

import com.app.storytel.challenge.model.ApplicationRole;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.respository.ApplicationRoleRepository;
import com.app.storytel.challenge.respository.LoginInformationRepository;
import com.app.storytel.challenge.respository.MessageRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageServiceTest {

    @Autowired
    private LoginInformationRepository loginInformationRepository;
    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageImpl messageService;
    @Autowired
    private LoginInformationImpl loginInformationImpl;

    private LoginInformation loginInformation;

    @BeforeAll
    void initTestRecords() {
        ApplicationRole applicationRole = new ApplicationRole();
        applicationRole.setRoleName("User");
        applicationRoleRepository.save(applicationRole);

        this.loginInformation = this.loginInformationRepository.findOneByEmailAddress("admin@yahoo.com");

    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveNewMessageTest() {
        LoginInformation owner = this.loginInformationImpl.findLoginInformation(1L);
        assertNotNull(owner, "Message owner empty");

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageContent("This is a very long content");
        messageRequest.setSubject("Subject of the message");

        Message testMessage = this.messageService.saveNewMessage(messageRequest, owner);

        assertAll(
                "Save Message Test",
                () -> assertNotNull(testMessage, "Save message test failed with nul"),
                () -> assertNotNull(testMessage.getId(), "Save message test failed with no ID"));

    }

    @Test
    void updateMessageTest() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageContent("This is a very long content");
        messageRequest.setSubject("Subject of the message");
        messageRequest.setViews(20);
        messageRequest.setId(4L);

        boolean updatedRecord = this.messageService.updateMessage(messageRequest, this.loginInformation);
        assertTrue(updatedRecord, "update message test failed");
    }

    @Test
    void findMessageTest() {
        Long messageId = 3L;
        Message message = this.messageService.findMessage(messageId);
        assertNotNull(message, "Finding message from");
    }

    @Test
    void fetchMessagesTest() {
        List<Message> messageList = this.messageService.fetchMessages(0, 5, "subject");
        assertAll(
                "Save Message Test",
                () -> assertNotNull(messageList, "Message list is empty"),
                () -> assertTrue(messageList.size() > 0, "Result set is less than zero"));
    }

    @Test
    void deleteMessageTest() {
        Long messageId = 5L;
        boolean deletedRecord = this.messageService.deleteMessage(messageId, this.loginInformation);
        assertTrue(deletedRecord, "Deleting message failed");
    }

    private ArrayList<Message> prepareMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            Message message = new Message();
            message.setMessageContent(String.format("This is a very long content %d", x));
            message.setSubject(String.format("Subject of the message %d", x));
            messages.add(message);
        }

        return messages;
    }
}
