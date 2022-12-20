package com.zoola.taskmanager.service;

import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(User entity) {
        userRepository.create(entity);
    }

    public User read(int id) throws Exception {
        return userRepository.read(id);
    }

    public void update(int id, User newUser) throws Exception {
        userRepository.update(id, newUser);
    }

    public void delete(int id) throws Exception {
        userRepository.delete(id);
    }
}