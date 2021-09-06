package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.model.CustomUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService, CustomUserDetailService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailServiceImpl.class);

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userService.getUser(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        var usr = user.map(CustomUserDetail::new).get();

        logger.warn(usr.getUsername());
        logger.warn(usr.getPassword());
        usr.getAuthorities().forEach(grantedAuthority -> logger.warn(grantedAuthority.getAuthority()));

        return usr;
    }

    @Override
    public Optional<String> getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return Optional.ofNullable(currentUserName);
        }
        return Optional.of("AnonymousUser");
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Optional<User> user = userService.getUser(authentication.getName());
            return user;
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getCurrentRoleName() {
        return Optional.empty();
    }

    @Override
    public Optional<Role> getCurrentRole() {
        return Optional.empty();
    }
}

