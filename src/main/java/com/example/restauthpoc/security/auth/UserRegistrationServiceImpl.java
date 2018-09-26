package com.example.restauthpoc.security.auth;

import com.example.restauthpoc.data.User;
import com.example.restauthpoc.data.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public boolean register(String username, String password) {

        User user = User.builder()
                .id(null)
                .username(username)
                .password(encoder.encode(password))
                .roles(ImmutableList.of("ROLE_USER"))
                .build();

        repository.save(user);

        return true;
    }
}
