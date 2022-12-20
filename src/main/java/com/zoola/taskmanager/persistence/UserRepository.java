package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.customExceptions.UserException;
import com.zoola.taskmanager.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {

    private final List<User> data = new ArrayList<>();

    public void create(User entity) {
        data.add(entity);
    }

    public User read(int id) throws UserException {
        return data.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .get();
    }

    public void update(int id, User newUser) throws UserException {
        for (int i = 0; i < data.size(); i++) {
            User oldUser = data.get(i);
            if (oldUser.getId() == id) {
                data.set(i, newUser);
            }
        }
    }

    public void delete(int id) throws UserException {
        data.removeIf(user -> Objects.equals(user.getId(), id));
    }
}