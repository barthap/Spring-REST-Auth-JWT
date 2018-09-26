package com.example.restauthpoc.data;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */

@Repository
public class InMemoryUserRepository implements UserRepository {

    private Map<Long, User> users = new HashMap<>();

    @Override
    public void save(User newUser) {

        if(newUser.getId() == null)
            newUser.setId(users.size()+1L);

        users.put(newUser.getId(), newUser);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    @Override
    public void update(User updatedUser) {
        users.replace(updatedUser.getId(), updatedUser);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public void deleteById(long id) {
        users.remove(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
