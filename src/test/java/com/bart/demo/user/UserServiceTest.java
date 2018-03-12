package com.bart.demo.user;

import com.bart.demo.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class UserServiceTest extends AbstractTest {

    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void before() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testCreateUser() {

        User user = new User(getRandomString(), new HashSet<>());
        user.setId(1L);

        given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(user);

        User createdUser = userService.createUser(new CreateUserDTO(user.getUsername()));

        assertEquals(user.getUsername(), createdUser.getUsername());
    }

    @Test
    public void testAddSubscription() {

        User user1 = new User(getRandomString(), new HashSet<>());
        user1.setId(1L);
        given(userRepository.findById(user1.getId())).willReturn(Optional.of(user1));

        User user2 = new User(getRandomString(), new HashSet<>());
        user2.setId(2L);
        given(userRepository.findById(user2.getId())).willReturn(Optional.of(user2));

        given(userRepository.save(any(User.class))).willReturn(user1);

        User user = userService.addSubscription(user1.getId(), new AddSubscriptionDTO(user2.getId()));

        assertEquals(1, user.getSubscribedUsers().size());
    }
}
