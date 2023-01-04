package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.customExceptions.UserNotFoundException;
import com.zoola.taskmanager.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final List<User> data;

    public void create(User entity) {
        data.add(entity);
    }

    public User read(int id) throws UserNotFoundException {
        try {
            return data.stream()
                    .filter(user -> user.getId() == id)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException noSuchElementException) {
            throw new UserNotFoundException();
        }
    }

    public void update(int id, User newUser) throws UserNotFoundException {
        for (int i = 0; i < data.size(); i++) {
            User oldUser = data.get(i);
            if (oldUser.getId() == id) {
                data.set(i, newUser);
            }
        }
    }

    public void delete(int id) throws UserNotFoundException {
        data.removeIf(user -> Objects.equals(user.getId(), id));
    }
}