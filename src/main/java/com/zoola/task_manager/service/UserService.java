package com.zoola.task_manager.service;

import com.zoola.task_manager.domain.User;
import com.zoola.task_manager.supportClasses.crudInterface.CRUD;

import java.util.ArrayList;
import java.util.List;

public class UserService implements CRUD<User> {

    private final List<User> users = new ArrayList<>();

    @Override
    public void create(User entity) {
        users.add(entity);
    }

    @Override
    public User read(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void update(int id) throws Exception {
        for(User user : users) {
            if(user.getId() == id) {
                //update user
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        assert users.size() != 0;
        users.removeIf(user -> user.getId() == id);
    }
}