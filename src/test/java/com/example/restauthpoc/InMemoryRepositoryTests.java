package com.example.restauthpoc;

import com.example.restauthpoc.data.User;
import com.example.restauthpoc.data.UserRepository;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryRepositoryTests {

    private String USERNAME = "user";
    private String PASSWD = "pass";
    private long ID = 1L;

    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() {
        //clear repo and save 1 test user before each test
        repository.deleteAll();
        repository.save(createTestUser());
    }

    @Test
    public void createTest() {
        User newUser = User.builder().id(2L).build();

        repository.save(newUser);

        assertThat(repository.findAll().size(), is(2));
    }

    @Test
    public void getByIdTest() {

        Optional<User> user = repository.findById(ID);

        assertTrue(user.isPresent());
        assertThat(user.get().getPassword(), equalTo(PASSWD));
    }

    @Test
    public void getByUsernameTest() {

        assertTrue(repository.findByUsername(USERNAME).isPresent());
        assertThat(repository.findByUsername(USERNAME).get().getUsername(), equalTo(USERNAME));
    }

    @Test
    public void updateTest() {

        val NEW_PASS = "p@sswd1234";
        User updated = createTestUser();
        updated.setPassword(NEW_PASS);

        repository.update(updated);

        assertTrue(repository.findById(updated.getId()).isPresent());
        assertThat(repository.findById(updated.getId()).get().getPassword(), is(NEW_PASS));
    }

    @Test
    public void deleteTest() {
        repository.deleteById(ID);

        assertTrue(repository.findAll().isEmpty());
    }


    private User createTestUser() {
        return User.builder().id(ID).username(USERNAME).password(PASSWD).build();
    }
}
