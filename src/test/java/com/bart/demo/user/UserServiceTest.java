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
import static org.mockito.Mockito.when;

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

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(new CreateUserDTO(user.getUsername()));

        assertEquals(user.getUsername(), createdUser.getUsername());
    }

    @Test
    public void testAddSubscription() {

        User user1 = new User(getRandomString(), new HashSet<>());
        user1.setId(1L);
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        User user2 = new User(getRandomString(), new HashSet<>());
        user2.setId(2L);
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));

        when(userRepository.save(any(User.class))).thenReturn(user1);

        User user = userService.addSubscription(user1.getId(), new AddSubscriptionDTO(user2.getId()));

        assertEquals(1, user.getSubscribedUsers().size());
    }
}
