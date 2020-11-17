package com.app.storytel.challenge.service;

import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.respository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author samsonfagade
 */
@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveNewMessage(MessageRequest messageRequest, LoginInformation owner) {

        if (messageRequest != null && owner != null) {
            log.info("Object validation passed {}", messageRequest);
            Message newMessage = this.messageRepository.save(new Message(null, messageRequest.getSubject(),
                    messageRequest.getMessageContent(), 0, owner, null, null));

            if (newMessage.getId() != null) {
                return newMessage;
            }
        } else {
            log.info("Message-request = {} Owner = {}", messageRequest, owner);
        }
        return null;
    }

    public Boolean updateMessage(Long message_id, MessageRequest messageRequest) {

        if (message_id != null && messageRequest != null) {
            Optional<Message> optionalMessage = messageRepository.findById(message_id);
            if (optionalMessage.isPresent()) {
                Message existingMessage = optionalMessage.get();
                existingMessage.setSubject(messageRequest.getSubject());
                existingMessage.setMessageContent(messageRequest.getMessageContent());
                if (existingMessage.getViews() != null && existingMessage.getViews() < messageRequest.getViews()) {
                    existingMessage.setViews(messageRequest.getViews());
                }

                this.messageRepository.save(existingMessage);
                log.info("Message updated successfully {}", existingMessage.getId());
                return true;

            } else {
                log.info("Message not in record");
            }
        } else {
            log.info("Message-request = {} login-id = {}", messageRequest, message_id);
        }

        return false;
    }

    public Boolean deleteMessage(Long message_id) {

        if (message_id != null) {
            Message message = this.findMessage(message_id);

            if (message != null) {
                this.messageRepository.delete(message);
                log.info("Message deleted successfully");
                return true;
            } else {
                log.info("findMessage returned null");
            }
        } else {
            log.info("Message-ID = {}", message_id);
        }

        return false;
    }

    public List<Message> fetchMessages(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Message> pagedResult = this.messageRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            log.info("Records search returned empty");
        }

        return null;
    }

    public Message findMessage(Long message_id) {

        if (message_id != null) {
            Optional<Message> optionalMessage = this.messageRepository.findById(message_id);
            if (optionalMessage.isPresent()) {
                log.info("Found message with ID: {}", message_id);
                return optionalMessage.get();
            } else {
                log.info("Invalid message ID: {}, specified for delete", message_id);
            }
        }
        return null;
    }
}
