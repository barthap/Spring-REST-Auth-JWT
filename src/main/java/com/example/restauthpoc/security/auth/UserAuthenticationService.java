package com.example.restauthpoc.security.auth;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return a session token when login succeeds
     */
    Optional<String> login(String username, String password);

    /**
     * Finds user session by a token
     *
     * @param token data dao key
     * @return
     */
    Optional<UserSession> findByToken(String token);

    /**
     * Logs out the given UserSession
     *
     * @param user the session object to logout
     */
    void logout(UserSession user);
}