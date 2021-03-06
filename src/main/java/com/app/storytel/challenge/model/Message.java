package com.app.storytel.challenge.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author samsonfagade
 */
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "messages")
@AttributeOverride(name = "id", column = @Column(name = "message_id", 
        nullable = false, columnDefinition = "BIGINT"))
public class Message extends StorytelModelBase implements Serializable {

    @NotNull(message = "Message subject cannot be blank")
    @Size(max = 150, message = "Message subject should not be longer than 150 characters")
    @Column(name = "message_subject")
    private String subject;
    @NotNull(message = "Message content cannot be blank")
    @Column(name = "message_content", length = 1000)
    @Size(max = 1000, message = "Message content should not be longer than 1000 characters")
    private String messageContent;
    @Column(name = "view_count", columnDefinition = "int default '0'")
    private Integer views;
    @JoinColumn(name = "login_id", referencedColumnName = "login_id")
    @ManyToOne
    private LoginInformation owner;

    public Message(Long messageId, String subject, String messageContent, 
            Integer views, LoginInformation owner, LocalDateTime created, 
            LocalDateTime modified) {
        this.subject = subject;
        this.messageContent = messageContent;
        this.views = views;
        this.owner = owner;
        this.setId(messageId);
        this.setCreated(created);
        this.setModified(modified);
    }

}
