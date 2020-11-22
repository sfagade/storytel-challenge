package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.component.LoginInformationComponent;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.MessageRequest;
import com.app.storytel.challenge.payload.response.ErrorMessage;
import com.app.storytel.challenge.payload.response.MessageResponse;
import com.app.storytel.challenge.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to serve all REST CRUD functionalities for Messages the
 * client has to be authenticated to use these endpoints. Authorization is
 * confirmed using jwt token which is generated upon successful login
 */
@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessagesResource {

    private final MessageService messageService;
    private final LoginInformationComponent loginInformationComponent;

    @Autowired
    private MessagesResource(MessageService messageService, LoginInformationComponent loginInformationComponent) {
        this.messageService = messageService;
        this.loginInformationComponent = loginInformationComponent;
    }

    @PostMapping
    public ResponseEntity<?> createNewMessage(@Valid @RequestBody MessageRequest messageRequest) {
        log.info("Call made to saveNewMessage method");
        ErrorMessage errorMessage;
        Message message = this.messageService.saveNewMessage(messageRequest,
                loginInformationComponent.fetchLoginUserInformation());

        if (message != null) {
            MessageResponse messageResponse = new MessageResponse(message.getId(), message.getSubject(),
                    message.getMessageContent(), message.getViews(), message.getOwner().getId(), message.getCreated(),
                    message.getModified());
            log.info("Call to saveNewMessage was successful");
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            log.info("Failed to save new message information for {}", messageRequest);
            errorMessage = new ErrorMessage("Create failed", new Date());
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> updateExistingMessage(@Valid @RequestBody MessageRequest messageRequest) {
        log.info("Call made to updateExistingMessage method");
        ErrorMessage errorMessage;

        if (messageRequest.getViews() != null) {
            if (this.messageService.updateMessage(messageRequest,
                    loginInformationComponent.fetchLoginUserInformation())) {

                log.info("Call to updateExistingMessage was successful");
                Message updatedMessage = this.messageService.findMessage(messageRequest.getId());
                MessageResponse messageResponse = new MessageResponse(updatedMessage.getId(), updatedMessage.getSubject(),
                        updatedMessage.getMessageContent(), updatedMessage.getViews(), updatedMessage.getOwner().getId(),
                        updatedMessage.getCreated(), updatedMessage.getModified());
                return new ResponseEntity<>(messageResponse, HttpStatus.OK);
            } else {
                log.info("Failed to update message with record: {}", messageRequest);
                errorMessage = new ErrorMessage("Bad Request", new Date());
            }
        } else {
            /**
             * alternatively I could create a new request object for updates and
             * make views required on that object, given enough time that would
             * be the preferred implementation
             */
            log.error("views not set on request");
            errorMessage = new ErrorMessage("Messing field: views", new Date());
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        log.info("Call made to deleteMessage method");

        if (this.messageService.deleteMessage(messageId, loginInformationComponent.fetchLoginUserInformation())) {
            log.info("Message with id {} deleted", messageId);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Failed to delete message");
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
