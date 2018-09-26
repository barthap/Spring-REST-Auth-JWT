package com.example.restauthpoc;

import com.example.restauthpoc.security.auth.UserRegistrationService;
import com.example.restauthpoc.data.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRegistrationTests {

    @Autowired
    private UserRegistrationService registrationService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void registerTest() {
        final String USERNAME = "user123";

        final boolean result = registrationService.register(USERNAME, "pass");

        assertTrue(result);
        assertTrue(userRepository.findByUsername(USERNAME).isPresent());
    }
}
