package com.zoola.taskmanager.rest.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoola.taskmanager.controller.UserController;
import com.zoola.taskmanager.customExceptions.UserNotFoundException;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.UserRepositoryInterface;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.zoola.taskmanager")
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepositoryInterface userRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
        User actualUser =  objectMapper.readValue(actualUserString, User.class);

         // Then
         assertEquals(expectedUser, actualUser);
    }

    @Test
    public void createUser() throws Exception {
        //Given
        User user = new User(1, "user-1@email.com", "user-1");

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(objectMapper.writeValueAsString(user))
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
                        .content(objectMapper.writeValueAsString(newUser))
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
        assertThrows(UserNotFoundException.class, () -> userRepository.read(1));
    }
}