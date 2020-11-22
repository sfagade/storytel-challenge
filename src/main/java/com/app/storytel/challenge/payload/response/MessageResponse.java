package com.app.storytel.challenge.payload.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
