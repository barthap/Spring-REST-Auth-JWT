package com.example.restauthpoc.util;

import com.example.restauthpoc.data.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
public interface IRepository<T, ID> {
    void create(T newItem);

    Collection<T> getAll();
    Optional<T> getById(ID id);

    void update(T updatedItem);

    //void deleteById(T item);
    void delete(ID id);

    void deleteAll();
}
