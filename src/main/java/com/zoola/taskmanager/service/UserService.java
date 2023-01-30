package com.zoola.taskmanager.service;

import com.zoola.taskmanager.customExceptions.UserNotFoundException;
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

    public User read(int id) throws UserNotFoundException {
        return userRepository.read(id);
    }

    public User update(int id, User newUser) throws UserNotFoundException {
        userRepository.update(id, newUser);
        return userRepository.read(id);
    }

    public void delete(int id) throws UserNotFoundException {
        userRepository.delete(id);
    }

    //CUSTOM
    public void creareTask() {}

}