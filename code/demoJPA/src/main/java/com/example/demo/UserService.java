package com.example.demo;

import com.example.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> getUser(String username, String password) {
        return userRepository.findUser(username, password);
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }

    public User checkUsername(String username) {
        return userRepository.checkUsername(username);
    }

    public User checkEmail(String email) {
        return userRepository.checkEmail(email);
    }

    public User checkPhone(String phone) {
        return userRepository.checkPhone(phone);
    }

}
