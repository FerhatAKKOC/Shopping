package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserDetailService {

    Optional<String> getCurrentUserName();

    Optional<User> getCurrentUser();

    Optional<String> getCurrentRoleName();

    Optional<Role> getCurrentRole();
}
