package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.response.ErrorMessage;
import com.app.storytel.challenge.payload.response.MessageResponse;
import com.app.storytel.challenge.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** This class is used to serve REST endpoints that are publicly available to clients
 * i.e. does not require authentication nor authorization
 * */
@Slf4j
@RestController
@RequestMapping("/api/public")
public class PublicMessages {

    private final MessageService messageService;

    @Autowired
    public PublicMessages(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<?> fetchMessages(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "limit", defaultValue = "30") int limit,
                                           @RequestParam(value = "order", defaultValue = "id") String order) {
        log.info("Call made to fetchMessages method");
        ErrorMessage errorMessage;
        List<Message> messageList = this.messageService.fetchMessages(page, limit, order);

        if (messageList != null && messageList.size() > 0) {
            List<MessageResponse> messageResponseList = messageList.stream().map(
                    message -> new MessageResponse(message.getId(), message.getSubject(), message.getMessageContent(),
                            message.getViews(), message.getOwner().getId(), message.getCreated(), message.getModified())
            ).collect(Collectors.toList());

            log.info("Total records matching: {}", messageResponseList.size());
            return new ResponseEntity<>(messageResponseList, HttpStatus.OK);
        } else {
            log.info("Did not find any record for request");
            errorMessage = new ErrorMessage("Not found", new Date());
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<?> findMessage(@PathVariable Long messageId) {
        log.info("Call made to fetchMessages method");
        ErrorMessage errorMessage;
        Message message = this.messageService.findMessage(messageId);

        if (message != null) {
            MessageResponse messageResponse = new MessageResponse(message.getId(), message.getSubject(),
                    message.getMessageContent(), message.getViews(), message.getOwner().getId(), message.getCreated(),
                    message.getModified());
            log.info("Call to fetchMessages successful");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            log.info("Did not find any record with ID {}", messageId);
            errorMessage = new ErrorMessage("Not found", new Date());
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }
}
