package com.zoola.taskmanager.rest.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoola.taskmanager.controller.UserController;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@ComponentScan("com.zoola.taskmanager")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUser() throws Exception {
        // Given
        User expectedUser = new User(1, "user-1@email.com", "user-1");
        userRepository.create(expectedUser);

        // When
        var actualUserString = mockMvc.perform(MockMvcRequestBuilders
                .get("/user/1"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        User actualUser = toObject(User.class, actualUserString);

         // Then
         assertEquals(expectedUser, actualUser);
    }

    @Test
    public void createUser() throws Exception {
        //Given
        User user = new User(1, "user-1@email.com", "user-1");

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //Then
        assertEquals(userRepository.read(1), user);
    }

    @Test
    public void updateUser() throws Exception {
        //Given
        User oldUser = new User(1, "user-1@email.com", "user-1");
        User newUser = new User(1, "newUser-1@gmail.com", "newUser-1");
        userRepository.create(oldUser);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                        .content(asJsonString(newUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
        //Then
        assertEquals(userRepository.read(1), newUser);
    }

    @Test
    public void deleteUser() throws Exception {
        //Given
        User user = new User(1, "user-1@email.com", "user-1");
        userRepository.create(user);

        //When
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isAccepted());
        //Then
        assertThrows(NoSuchElementException.class, () -> userRepository.read(1));
    }

    /*
    Source:
    http://www.masterspringboot.com/testing/testing-spring-boot-applications-with-mockmvc/
    */
    /**@// *FIXME: 29.12.2022 ObjectMapper should be a bean*/
    public static String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static <T> T toObject(Class<T> clazz, String content) throws JsonProcessingException {
        return new ObjectMapper().readValue(content, clazz);
    }
}