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
        ErrorMessage errorMessage;
        Message message = this.messageService.saveNewMessage(messageRequest,
                loginInformationComponent.fetchLoginUserInformation());

        if (message != null) {
            MessageResponse messageResponse = new MessageResponse(message.getId(), message.getSubject(),
                    message.getMessageContent(), message.getViews(), message.getOwner().getId(), message.getCreated(),
                    message.getModified());
            log.debug("Call to saveNewMessage was successful");
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            log.error("Failed to save new message information for {}", messageRequest);
            errorMessage = new ErrorMessage("Create failed", new Date());
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> updateExistingMessage(@Valid @RequestBody MessageRequest messageRequest) {
        ErrorMessage errorMessage;

        if (messageRequest.getViews() != null) {
            if (this.messageService.updateMessage(messageRequest,
                    loginInformationComponent.fetchLoginUserInformation())) {

                log.debug("Call to updateExistingMessage was successful");
                Message updatedMessage = this.messageService.findMessage(messageRequest.getId());
                MessageResponse messageResponse = new MessageResponse(updatedMessage.getId(), updatedMessage.getSubject(),
                        updatedMessage.getMessageContent(), updatedMessage.getViews(), updatedMessage.getOwner().getId(),
                        updatedMessage.getCreated(), updatedMessage.getModified());
                return new ResponseEntity<>(messageResponse, HttpStatus.OK);
            } else {
                log.error("Failed to update message with record: {}", messageRequest);
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

        if (this.messageService.deleteMessage(messageId, loginInformationComponent.fetchLoginUserInformation())) {
            return ResponseEntity.noContent().build();
        } else {
            log.error("Failed to delete message");
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> fetchMessages(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "limit", defaultValue = "30") int limit,
                                           @RequestParam(value = "order", defaultValue = "id") String order) {
        ErrorMessage errorMessage;
        List<Message> messageList = this.messageService.fetchMessages(page, limit, order);

        if (messageList != null && messageList.size() > 0) {
            List<MessageResponse> messageResponseList = messageList.stream().map(
                    message -> new MessageResponse(message.getId(), message.getSubject(), message.getMessageContent(),
                            message.getViews(), message.getOwner().getId(), message.getCreated(), message.getModified())
            ).collect(Collectors.toList());

            log.debug("Total records matching: {}", messageResponseList.size());
            return new ResponseEntity<>(messageResponseList, HttpStatus.OK);
        } else {
            log.error("Did not find any record for request");
            errorMessage = new ErrorMessage("Not found", new Date());
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<?> findMessage(@PathVariable Long messageId) {
        ErrorMessage errorMessage;
        Message message = this.messageService.findMessage(messageId);

        if (message != null) {
            MessageResponse messageResponse = new MessageResponse(message.getId(), message.getSubject(),
                    message.getMessageContent(), message.getViews(), message.getOwner().getId(), message.getCreated(),
                    message.getModified());
            log.debug("Call to fetchMessages successful");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            log.error("Did not find any record with ID {}", messageId);
            errorMessage = new ErrorMessage("Not found", new Date());
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }
}
