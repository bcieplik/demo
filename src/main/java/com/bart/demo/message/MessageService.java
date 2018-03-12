package com.bart.demo.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message postMessage(CreateMessageWithUserDTO createMessageWithUserDTO);
    Message postMessage(Long userId, CreateMessageDTO createMessageDTO);
    Page<Message> getMessages(Pageable pageable);
    Page<Message> getWall(Long userId, Pageable pageable);
    Page<Message> getTimeline(Long userId, Pageable pageable);
}
