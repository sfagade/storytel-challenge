package com.app.storytel.challenge.respository;

import com.app.storytel.challenge.model.Message;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author samsonfagade
 */
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    
}
