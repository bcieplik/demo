package com.bart.demo.user;

import com.bart.demo.AbstractTest;
import com.bart.demo.message.CreateMessageDTO;
import com.bart.demo.message.CreateMessageWithUserDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends AbstractTest {

    @Test
    public void testCreateMessageForUser() throws Exception {

        User user = getRandomUser();
        String message = getRandomString();
        CreateMessageDTO createMessageDTO = new CreateMessageDTO(message);
        String data = convertToJson(createMessageDTO);

        mockMvc.perform(post("/api/users/" + user.getId() + "/wall")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(data))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.text", Matchers.is(message)))
                .andExpect(jsonPath("$.creator.username", Matchers.is(user.getUsername())))
        ;
    }

    @Test
    public void testWall() throws Exception {

        User user = getRandomUser();
        String[] messages = new String[]{"1", "2", "3", "4", "5"};
        Arrays.asList(messages).forEach(message ->
                messageService.postMessage(user.getId(),  new CreateMessageDTO(message)));

        mockMvc.perform(get("/api/users/" + user.getId() + "/wall")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", Matchers.hasSize(messages.length)))
                .andExpect(jsonPath("$.content[*].text", Matchers.containsInAnyOrder(messages)))
                .andExpect(jsonPath("$.content[0].text", Matchers.is(messages[messages.length - 1])))
        ;
    }

    @Test
    public void testAddSubscription() throws Exception {

        User user = getRandomUser();
        User user2 = getRandomUser();

        AddSubscriptionDTO addSubscriptionDTO = new AddSubscriptionDTO(user2.getId());
        mockMvc.perform(post("/api/users/" + user.getId() + "/subscriptions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertToJson(addSubscriptionDTO)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void testGetSubscribtions() throws Exception {

        User user = getRandomUser();
        User user2 = getRandomUser();
        userService.addSubscription(user.getId(), new AddSubscriptionDTO(user2.getId()));

        AddSubscriptionDTO addSubscriptionDTO = new AddSubscriptionDTO(user2.getId());
        mockMvc.perform(get("/api/users/" + user.getId() + "/subscriptions")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", Matchers.is(user2.getUsername())))
        ;
    }

    @Test
    public void testTimeline() throws Exception {

        User user1 = getRandomUser();
        User user2 = getRandomUser();
        User user3 = getRandomUser();

        userService.addSubscription(user1.getId(), new AddSubscriptionDTO(user2.getId()));
        userService.addSubscription(user1.getId(), new AddSubscriptionDTO(user3.getId()));

        String[] messages1 = new String[]{"1", "2", "3", "4", "5"};
        Arrays.asList(messages1).forEach(message ->
                messageService.postMessage(user2.getId(),  new CreateMessageDTO(message)));

        String[] messages2 = new String[]{"6", "7", "8", "9", "10"};
        Arrays.asList(messages2).forEach(message ->
                messageService.postMessage(user3.getId(),  new CreateMessageDTO(message)));

        String[] combined = new String[(messages1.length + messages2.length)];
        System.arraycopy(messages1, 0, combined, 0, messages1.length);
        System.arraycopy(messages2, 0, combined, messages1.length, messages2.length);

        AddSubscriptionDTO addSubscriptionDTO = new AddSubscriptionDTO(user2.getId());
        mockMvc.perform(get("/api/users/" + user1.getId() + "/timeline")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertToJson(addSubscriptionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].text", Matchers.containsInAnyOrder(combined)))
                .andExpect(jsonPath("$.content[0].text", Matchers.is(combined[combined.length - 1])))
        ;
    }
}
