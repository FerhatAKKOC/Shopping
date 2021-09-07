package com.example.demo.controller;

import com.example.demo.constant.UserRoles;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.OpenFilesEvent;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RoleController {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<String> who() {
        Optional<User> user = customUserDetailService.getCurrentUser();
        StringBuilder sb = new StringBuilder();
        if (user.isPresent()) {
            sb.append("<h1>Welcome</h1>");
            sb.append("\nUser: " + user.get().getUsername() + "\n");
            sb.append("<br>Roles: ");
            user.get().getRoles().forEach(role -> sb.append(role.getRole() + ", "));
        } else {
            sb.append("\nUserName: anonymousUser\n<br>Roles: ROLE_ANONYMOUS");
        }
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/admin")
    public ResponseEntity<String> loginAdmin() {
        Optional<User> user = customUserDetailService.getCurrentUser();
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Welcome</h1>");
        sb.append("\nUser: " + user.get().getUsername() + "\n");
        sb.append("<br>Roles: ");
        user.get().getRoles().forEach(role -> sb.append(role.getRole() + ", "));
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/customer")
    public ResponseEntity<String> loginCustomer() {
        Optional<User> user = customUserDetailService.getCurrentUser();
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Welcome</h1>");
        sb.append("\nUser: " + user.get().getUsername() + "\n");
        sb.append("<br>Roles: ");
        user.get().getRoles().forEach(role -> sb.append(role.getRole() + ", "));
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

}
