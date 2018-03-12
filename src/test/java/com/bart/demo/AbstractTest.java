package com.bart.demo;

import com.bart.demo.message.MessageService;
import com.bart.demo.user.CreateUserDTO;
import com.bart.demo.user.User;
import com.bart.demo.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@TestPropertySource("classpath:/application-test.properties")
public abstract class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserService userService;

    @Autowired
    protected MessageService messageService;

    @Getter
    private Random random = new Random();

    protected String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getRandomString() {
        return random.nextInt(Integer.MAX_VALUE - 1) + "";
    }

    protected User getRandomUser() {
        return userService.createUser(new CreateUserDTO(getRandomString()));
    }
}
