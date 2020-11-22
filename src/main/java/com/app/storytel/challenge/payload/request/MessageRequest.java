package com.app.storytel.challenge.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author samsonfagade
 */
@NoArgsConstructor
@Data
public class MessageRequest {

    private Long id;
    @NotNull(message = "Message subject cannot be blank")
    @Size(max = 150, message = "Message subject should not be longer than 150 characters")
    private String subject;
    @NotNull(message = "Message content cannot be blank")
    @Size(max = 1000, message = "Message content should not be longer than 1000 characters")
    private String messageContent;
    private Integer views;

    public MessageRequest(Long messageId, String subject, String messageContent, Integer views) {
        this.setId(messageId);
        this.setSubject(subject);
        this.setMessageContent(messageContent);
        this.setViews(views);
    }
}
