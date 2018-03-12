package com.bart.demo.message;

import com.bart.demo.config.error.NotFoundException;
import com.bart.demo.user.CreateUserDTO;
import com.bart.demo.user.User;
import com.bart.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Message postMessage(CreateMessageWithUserDTO createMessageDTO) {

        User user = userService
                .findByUsername(createMessageDTO.getUsername())
                .orElseGet(() -> userService.createUser(new CreateUserDTO(createMessageDTO.getUsername())));

        String text = createMessageDTO.getText();
        Message message = new Message(user, text);
        return messageRepository.save(message);
    }

    @Override
    public Message postMessage(Long userId, CreateMessageDTO createMessageDTO) {

        User user = userService
                .findById(userId)
                .orElseThrow(NotFoundException::new);

        String text = createMessageDTO.getText();
        Message message = new Message(user, text);
        return messageRepository.save(message);
    }

    public Page<Message> getMessages(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public Page<Message> getWall(Long userId, Pageable pageable) {
        User user = userService.findById(userId).orElseThrow(NotFoundException::new);
        PageRequest pageRequest = new PageRequest(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "createdAt")
        );
        return messageRepository.findByCreator(user, pageRequest);
    }

    public Page<Message> getTimeline(Long userId, Pageable pageable) {
        User user = userService.findById(userId).orElseThrow(NotFoundException::new);
        Set<User> subscribedUsers = user.getSubscribedUsers();
        if (subscribedUsers.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }
        PageRequest pageRequest = new PageRequest(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "createdAt")
        );
        return messageRepository.findByCreators(subscribedUsers, pageRequest);
    }
}
