package com.app.storytel.challenge.model.listener;

import com.app.storytel.challenge.model.StorytelModelBase;
import java.time.LocalDateTime;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Model listener class to perform certain functionalities when specific model
 * events are triggered. I'm listening to pre-update and pre-persist events for
 * this project
 *
 * @author samsonfagade
 */
public class RecordUpdateListener {

    @PreUpdate
    public void setUpdateDates(StorytelModelBase ldrAsb) {
        ldrAsb.setModified(LocalDateTime.now());
    }

    @PrePersist
    public void setCreateDates(StorytelModelBase ldrAsb) {
        ldrAsb.setModified(LocalDateTime.now());
        ldrAsb.setCreated(LocalDateTime.now());
    }
}
