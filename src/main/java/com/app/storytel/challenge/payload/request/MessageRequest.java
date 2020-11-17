package com.app.storytel.challenge.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author samsonfagade
 */
@NoArgsConstructor
@Data
public class MessageRequest {

    @NotNull(message = "Message subject cannot be blank")
    @Size(max = 150, message = "Message subject should not be longer than 150 characters")
    private String subject;
    @NotNull(message = "Message content cannot be blank")
    private String messageContent;
    private Integer views;
}
