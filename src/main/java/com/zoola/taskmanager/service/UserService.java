package com.zoola.taskmanager.service;

import com.zoola.taskmanager.customExceptions.UserException;
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

    public User read(int id) throws UserException {
        return userRepository.read(id);
    }

    public User update(int id, User newUser) throws UserException {
        userRepository.update(id, newUser);
        return userRepository.read(id);
    }

    public void delete(int id) throws UserException {
        userRepository.delete(id);
    }
}