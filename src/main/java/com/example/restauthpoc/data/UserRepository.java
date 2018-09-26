package com.example.restauthpoc.data;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */

/**
 * This can be replaced with Spring Data repository
 * Currently, to demo and test purposes, it has an in-memory implementation
 * @see InMemoryUserRepository
 */
public interface UserRepository {

    void save(User newUser);

    Collection<User> findAll();
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);

    void update(User updatedUser);

    void delete(User user);
    void deleteById(long id);

    void deleteAll();

}
