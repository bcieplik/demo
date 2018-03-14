package com.bart.demo.message;

import com.bart.demo.AbstractTest;
import com.bart.demo.user.User;
import com.bart.demo.user.UserService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySetOf;
import static org.mockito.BDDMockito.given;

public class MessageServiceTest extends AbstractTest {

    @MockBean
    private UserService userService;

    @MockBean
    private MessageRepository messageRepository;

    private MessageService messageService;

    @Before
    public void before() {
        messageService = new MessageServiceImpl(messageRepository, userService);
    }

    @Test
    public void testPostMessageWithUser() {

        User user = new User(getRandomString(), new HashSet<>());
        given(userService.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        Message message = new Message(user, getRandomString());
        given(messageRepository.save(any(Message.class))).willReturn(message);

        Message newMessage = messageService.postMessage(new CreateMessageWithUserDTO(message.getText(), user.getUsername()));

        assertEquals(message.getText(), newMessage.getText());
        assertEquals(message.getCreator().getUsername(), newMessage.getCreator().getUsername());
    }

    @Test
    public void testPostMessageForUser() {

        User user = new User(getRandomString(), new HashSet<>());
        user.setId(1L);
        given(userService.findById(user.getId())).willReturn(Optional.of(user));

        Message message = new Message(user, getRandomString());
        given(messageRepository.save(any(Message.class))).willReturn(message);

        Message newMessage = messageService.postMessage(user.getId(), new CreateMessageDTO(message.getText()));

        assertEquals(message.getText(), newMessage.getText());
        assertEquals(message.getCreator().getUsername(), newMessage.getCreator().getUsername());
    }

    @Test
    public void testGetWall() {

        User user = new User(getRandomString(), new HashSet<>());
        user.setId(1L);
        given(userService.findById(user.getId())).willReturn(Optional.of(user));

        Page<Message> messages = new PageImpl<>(Lists.newArrayList(
                new Message(user, getRandomString()),
                new Message(user, getRandomString())
        ));
        given(messageRepository.findByCreator(any(User.class), any(Pageable.class))).willReturn(messages);

        Page<Message> result = messageService.getWall(user.getId(), new PageRequest(1, 1));
        assertEquals(messages.getSize(), result.getSize());
    }

    @Test
    public void testGetTimeline() {

        User user1 = new User(getRandomString(), new HashSet<>());
        user1.setId(1L);
        User user2 = new User(getRandomString(), new HashSet<>());
        user2.setId(2L);

        user1.getSubscribedUsers().add(user2);

        given(userService.findById(user1.getId())).willReturn(Optional.of(user1));

        Page<Message> messages = new PageImpl<>(Lists.newArrayList(
                new Message(user2, getRandomString()),
                new Message(user2, getRandomString())
        ));
        given(messageRepository.findByCreators(anySetOf(User.class), any(Pageable.class))).willReturn(messages);

        Page<Message> result = messageService.getTimeline(user1.getId(), new PageRequest(1, 1));
        assertEquals(messages.getSize(), result.getSize());
    }
}
