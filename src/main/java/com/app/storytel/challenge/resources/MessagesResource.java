package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.payload.response.MessageResponse;
import com.app.storytel.challenge.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessagesResource {

    private final MessageService messageService;

    @Autowired
    private MessagesResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> fetchMessages(@RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "limit", defaultValue = "30") int limit,
                                                               @RequestParam(value = "order", defaultValue = "id") String order) {
        log.info("Call made to fetchMessages method");
        List<Message> messageList = this.messageService.fetchMessages(page, limit, order);

        if (messageList != null && messageList.size() > 0) {
            List<MessageResponse> messageResponseList = messageList.stream().map(
                    message -> new MessageResponse(message.getId(), message.getSubject(), message.getMessageContent(),
                            message.getViews(), message.getCreated(), message.getModified())
            ).collect(Collectors.toList());

            log.info("Total records matching: {}", messageResponseList.size());
            return new ResponseEntity<>(messageResponseList, HttpStatus.OK);
        } else {
            log.info("Did not find any record for request");
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<MessageResponse> findMessage(@PathVariable Long messageId) {
        log.info("Call made to fetchMessages method");
        Message message = this.messageService.findMessage(messageId);

        if (message != null) {
            MessageResponse messageResponse = new MessageResponse(message.getId(), message.getSubject(),
                    message.getMessageContent(), message.getViews(), message.getCreated(), message.getModified());
            log.info("Call to fetchMessages successful");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            log.info("Did not find any record with ID {}", messageId);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createNewMessage(@RequestBody MessageRequest messageRequest) {
        log.info("Call made to saveNewMessage method");
        Message message = this.messageService.saveNewMessage(messageRequest, new LoginInformation());

        if (message != null) {
            MessageResponse messageResponse = new MessageResponse(message.getId(), message.getSubject(),
                    message.getMessageContent(), message.getViews(), message.getCreated(), message.getModified());
            log.info("Call to saveNewMessage was successful");
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            log.info("Failed to save new message information for {}", messageRequest);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<MessageResponse> updateExistingMessage(@RequestBody MessageRequest messageRequest) {
        log.info("Call made to updateExistingMessage method");

        if (this.messageService.updateMessage(messageRequest)) {
            log.info("Call to updateExistingMessage was successful");
            Message updatedMessage = this.messageService.findMessage(messageRequest.getId());
            MessageResponse messageResponse = new MessageResponse(updatedMessage.getId(), updatedMessage.getSubject(),
                    updatedMessage.getMessageContent(), updatedMessage.getViews(), updatedMessage.getCreated(),
                    updatedMessage.getModified());
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            log.info("Failed to update message with record: {}", messageRequest);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        log.info("Call made to deleteMessage method");

        if (this.messageService.deleteMessage(messageId)) {
            log.info("Message with id {} deleted", messageId);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Failed to delete message");
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}