package com.zoola.taskmanager.controller;

import com.zoola.taskmanager.customExceptions.UserException;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public User getUser(@PathVariable int id) throws Exception {
        return userService.read(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        userService.create(user);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public User updateUser(@PathVariable int id, @RequestBody User user) throws UserException {
        return userService.update(id, user);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable int id) throws UserException {
        userService.delete(id);
    }
}