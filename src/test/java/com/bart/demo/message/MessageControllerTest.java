package com.bart.demo.message;

import com.bart.demo.AbstractTest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MessageControllerTest extends AbstractTest {

    @Test
    public void testCreateMessageWithUser() throws Exception {

        String username = getRandomString();
        String message = getRandomString();
        CreateMessageWithUserDTO createMessageDTO = new CreateMessageWithUserDTO(message, username);
        String data = convertToJson(createMessageDTO);

        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(data))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.text", Matchers.is(message)))
                .andExpect(jsonPath("$.creator.username", Matchers.is(username)))
        ;
    }

}
