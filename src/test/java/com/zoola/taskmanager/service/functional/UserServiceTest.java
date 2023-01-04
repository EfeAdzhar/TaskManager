package com.zoola.taskmanager.service.functional;

import com.zoola.taskmanager.customExceptions.UserNotFoundException;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.service.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @MethodSource("creatingUser")
    public void userShouldBeCreatedAndRead(User entity) throws UserNotFoundException {
        userService.create(entity);
        assertEquals(userService.read(1), entity);
    }

    @ParameterizedTest
    @MethodSource("creatingUser")
    public void userShouldBeUpdated(User entity) throws UserNotFoundException {
        User newUser = new User(1, "new-user1@gmail.com", "name");
        userService.create(entity);
        userService.update(1, newUser);
        assertNotEquals(entity, userService.read(1));
    }

    @ParameterizedTest
    @MethodSource("creatingUser")
    public void userShouldBeDeletedFromList(User entity) throws UserNotFoundException {
        userService.create(entity);
        userService.delete(1);
        assertThrows(UserNotFoundException.class, () -> userService.read(1));
    }

    public static List<Arguments> creatingUser() {
        return List.of(
                Arguments.of(
                        new User(1, "user-1@email.com", "user-1")));
    }
}