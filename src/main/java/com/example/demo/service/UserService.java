package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String userName);

    List<User> saveAllUsers(List<User> userList);
}
