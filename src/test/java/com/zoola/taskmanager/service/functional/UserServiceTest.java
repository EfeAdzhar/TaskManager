package com.zoola.taskmanager.service.functional;

import com.zoola.taskmanager.customExceptions.UserException;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.UserRepository;
import com.zoola.taskmanager.service.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserService userService;

    // @BeforeAll
    // public void creatingUser(User user) throws UserException {
    //   userService.create(user);
    //}

    @ParameterizedTest
    @MethodSource("creatingUser")
    public void userShouldBeCreatedAndRead(User entity) throws UserException {
        userService.create(entity);
        assertEquals(userService.read(1), entity);
    }

    @ParameterizedTest
    @MethodSource("creatingUser")
    public void userShouldBeUpdated(User entity) throws UserException {
        User newUser = new User(1, "new-user1@gmail.com", "name");
        userService.create(entity);
        userService.update(1, newUser);
        assertNotEquals(entity, userService.read(1));
    }

    @ParameterizedTest
    @MethodSource("creatingUser")
    public void userShouldBeDeletedFromList(User entity) throws UserException {
        userService.create(entity);
        userService.delete(1);
        assertThrows(NoSuchElementException.class, () -> userService.read(1));
    }

    public static List<Arguments> creatingUser() {
        return List.of(
                Arguments.of(
                        new User(1, "user-1@email.com", "user-1")));
    }
}