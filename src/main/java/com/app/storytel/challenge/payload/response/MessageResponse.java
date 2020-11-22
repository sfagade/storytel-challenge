package com.app.storytel.challenge.payload.response;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author samsonfagade
 */
@NoArgsConstructor
@Data
public class MessageResponse {

    private Long id;
    private Long ownerId;
    private String subject;
    private String messageContent;
    private Integer views;
    private LocalDateTime created;
    private LocalDateTime modified;

    public MessageResponse(Long id, String subject, String messageContent, Integer views,
                           Long ownerId, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.subject = subject;
        this.messageContent = messageContent;
        this.views = views;
        this.created = created;
        this.modified = modified;
        this.ownerId = ownerId;
    }
}
