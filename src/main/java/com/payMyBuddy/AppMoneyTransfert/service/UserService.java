package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(User user) {
        userRepository.deleteById(user.getUserId());
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
}
