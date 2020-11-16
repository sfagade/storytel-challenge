package com.app.storytel.challenge.model;

import com.app.storytel.challenge.model.listener.RecordUpdateListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

/**
 *
 * @author samsonfagade
 */
@Data
@MappedSuperclass
@EntityListeners(RecordUpdateListener.class)
public abstract class StorytelModelBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;
}
