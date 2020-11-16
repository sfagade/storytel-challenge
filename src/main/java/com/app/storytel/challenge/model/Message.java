package com.app.storytel.challenge.model;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author samsonfagade
 */
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Table(name = "nessages")
@AttributeOverride(name = "id", column = @Column(name = "message_id", nullable = false, columnDefinition = "BIGINT"))
public class Message extends StorytelModelBase implements Serializable {

    @NotNull(message = "Message subject cannot be blank")
    @Size(max = 150, message = "Message subject should not be longer than 150 characters")
    @Column(name = "message_subject")
    private String subject;
    @NotNull(message = "Message content cannot be blank")
    @Column(name = "message_subject")
    private String messageContent;
    @Column(name = "view_count", columnDefinition = "int default '0'")
    private Integer views;
    @JoinColumn(name = "login_id", referencedColumnName = "login_id")
    @ManyToOne
    private LoginInformation owner;
}
