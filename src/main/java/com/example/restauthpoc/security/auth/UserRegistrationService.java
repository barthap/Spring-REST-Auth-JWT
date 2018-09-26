package com.example.restauthpoc.security.auth;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
public interface UserRegistrationService {

    //TODO: Replace with DTO

    /**
     * Registers a new user
     * @param username
     * @param password
     * @return true if registration is successful
     */
    boolean register(String username, String password);
}
