package com.app.storytel.challenge.service.impl;

import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.respository.MessageRepository;

import com.app.storytel.challenge.service.MessageService;
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
 * This class is used to implement all CRUD methods declared in MessageService
 * interface
 *
 * @author samsonfagade
 */
@Service
@Slf4j
public class MessageImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
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

    @Override
    public Boolean updateMessage(MessageRequest messageRequest, LoginInformation loggedInUser) {

        if (messageRequest != null) {
            Optional<Message> optionalMessage = messageRepository.findById(messageRequest.getId());

            if (optionalMessage.isPresent()) {
                Message existingMessage = optionalMessage.get();

                if (existingMessage.getOwner().getId().equals(loggedInUser.getId())) {
                    existingMessage.setSubject(messageRequest.getSubject());
                    existingMessage.setMessageContent(messageRequest.getMessageContent());
                    if (existingMessage.getViews() != null && existingMessage.getViews() < messageRequest.getViews()) {
                        existingMessage.setViews(messageRequest.getViews());
                    }

                    this.messageRepository.save(existingMessage);
                    log.info("Message updated successfully {}", existingMessage.getId());
                    return true;
                } else {
                    log.info("{} tried to edit other user's message", loggedInUser.getId());
                }
            } else {
                log.info("Message not in record");
            }
        } else {
            log.info("Message-request is null");
        }

        return false;
    }

    @Override
    public Boolean deleteMessage(Long message_id, LoginInformation loggedInUser) {

        if (message_id != null) {
            Message message = this.findMessage(message_id);

            if (message != null && loggedInUser.getId().equals(message.getOwner().getId())) {
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

    @Override
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

    @Override
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
