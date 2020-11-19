package com.app.storytel.challenge.service;

import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.model.Message;
import com.app.storytel.challenge.payload.request.MessageRequest;

import java.util.List;

public interface MessageService {
    Message saveNewMessage(MessageRequest messageRequest, LoginInformation owner);
    Boolean updateMessage(MessageRequest messageRequest);
    Boolean deleteMessage(Long message_id);
    List<Message> fetchMessages(Integer pageNo, Integer pageSize, String sortBy);
    Message findMessage(Long message_id);

}
