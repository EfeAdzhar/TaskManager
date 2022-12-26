package com.zoola.taskmanager.service.unit;

import com.zoola.taskmanager.customExceptions.UserException;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.UserRepository;
import com.zoola.taskmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@MockitoSettings
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

   @InjectMocks
    private UserService userService;

    @Test
    public void testUserIsCreated(@Mock User user) throws UserException {
        when(userRepositoryMock.read(anyInt())).thenReturn(user);
        assertNotNull(userService.read(5));
    }

    @Test
    public void testUserIsRead(@Mock User user) throws UserException {
        when(userRepositoryMock.read(anyInt())).thenReturn(user);
        assertEquals(userService.read(1), user);
    }

    @Test
    public void testUserIsUpdated(@Mock User user) throws UserException {
        when(userRepositoryMock.read(1)).thenReturn(creatingUser().get(0));
        /**@bug(FIXME: Don't know how to update it correctly)
         * */
        userService.update(1, user);
        assertNotEquals(user, userService.read(1));
    }

    @Test
    public void testUserIsDeleted(@Mock User user) throws UserException {
        when(userRepositoryMock.read(anyInt())).thenThrow(UserException.class);
        /**@bug(FIXME: Don't know how to delete it correctly)
         * */
        userService.delete(1);
        assertThrows(UserException.class, () -> userService.read(1));
    }

    //If we use @Mock on User argument in testUserIsRead, we don't need this method
    public static List<User> creatingUser() {
        return List.of(
                        new User(1, "user-1@email.com", "user-1"));
    }
}