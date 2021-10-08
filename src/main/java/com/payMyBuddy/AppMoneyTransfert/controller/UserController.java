package com.payMyBuddy.AppMoneyTransfert.controller;

import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public UserController (UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<Void> addUser(@Valid @RequestBody User user) {
        User userAdded = userService.addUser(user);
        return new ResponseEntity(userAdded, HttpStatus.CREATED);
    }

    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
        User userModified = userService.updateUser(user);
        return new ResponseEntity(userModified, HttpStatus.NO_CONTENT);

    }
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody User user) {
        userService.deleteUser(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users")
    public ResponseEntity<Void> getAllUsers() {
        Iterable<User> listUser = userService.getAllUsers();
        return new ResponseEntity(listUser, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Void> getUserById(@PathVariable("id") Integer id) {
        Optional<User> listUser = userService.getUserById(id);
        return new ResponseEntity(listUser, HttpStatus.OK);
    }

}
