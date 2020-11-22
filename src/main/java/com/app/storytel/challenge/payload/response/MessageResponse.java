package com.app.storytel.challenge.payload.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * This dto class is used to return message object data back to the REST client.
 * This provides a safe a friendly way to make sure we're only sharing data that
 * the client cares about
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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime created;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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
