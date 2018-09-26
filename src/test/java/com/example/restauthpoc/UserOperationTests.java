package com.example.restauthpoc;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */

import com.example.restauthpoc.security.auth.UserAuthenticationService;
import com.example.restauthpoc.security.auth.UserRegistrationService;
import com.example.restauthpoc.data.User;
import com.example.restauthpoc.data.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 * INTEGRATION TEST
 * PERFORMS TESTS OF USER OPERATIONS - LOGIN, LOGOUT
 * Tests both UserAuthenticationService and UserSessionService
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserOperationTests {

    @Autowired
    private UserAuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private PasswordEncoder encoder;


    private String USERNAME = "user";
    private String PASSWORD = "pass";


    @Before
    public void setUp() {
        User usr = getSampleUser();
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(Optional.of(usr));
    }

    @Test
    public void loginByUsernamePasswordTest() {
        Optional<String> token = authenticationService.login(USERNAME, PASSWORD);
        assertTrue(token.isPresent());
    }

    @Test
    public void loginBadCredentialsTest() {
        Optional<String> token = authenticationService.login(USERNAME, "wrong");
        assertFalse(token.isPresent());
    }

    @Test
    public void getUserSessionByTokenTest() {
        /*
         * TODO: Implement test in 2 variants:
         * - with valid token
         * - with invalid token
         */
    }

    @Test
    public void logoutTest() {
        //TODO: Implement test
    }

    private User getSampleUser() {

          return User.builder()
                .id(1L)
                .username(USERNAME)
                .password(encoder.encode(PASSWORD))
                .build();
    }
}
