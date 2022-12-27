package com.zoola.taskmanager.rest.functional;

import com.zoola.taskmanager.controller.UserController;
import com.zoola.taskmanager.customExceptions.UserException;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@MockitoSettings
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void getUser(@Mock User user) throws Exception {
        when(userService.read(1)).thenReturn(user);
        assertEquals(userController.getUser(1), user);
    }

    @Test
    public void createUser(@Mock User user) throws UserException {
        when(userService.read(1)).thenReturn(user);
        userController.createUser(user);
        assertEquals(userService.read(1), user);

    }
}