package com.zoola.taskmanager.service.unit;

import com.zoola.taskmanager.customExceptions.UserException;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.UserRepository;
import com.zoola.taskmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

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
    public void testUserIsCreated(@Mock User user) {
        userService.create(user);
        Mockito.verify(userRepositoryMock).create(user);
    }

    @Test
    public void testUserIsRead(@Mock User user) throws UserException {
        //given
        when(userRepositoryMock.read(anyInt())).thenReturn(user);

        //when
        User readUser = userService.read(1);

        //then
        Mockito.verify(userRepositoryMock).read(1);
        assertEquals(readUser, user);
    }

    @Test
    public void testUserIsUpdated(@Mock User user, @Mock User newUser) throws UserException {
        //given
        when(userRepositoryMock.read(1)).thenReturn(newUser);

        //when
        User actualUser = userService.update(1, user);

        //then
        Mockito.verify(userRepositoryMock).update(1, user);
        assertEquals(actualUser, newUser);
    }

    @Test
    public void testUserIsDeleted(@Mock User user) throws UserException {
        //given
        int id = 1;

        //when
        userService.delete(id);

        //then
        Mockito.verify(userRepositoryMock).delete(id);
    }
}