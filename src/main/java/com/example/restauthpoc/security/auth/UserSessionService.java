package com.example.restauthpoc.security.auth;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
import java.util.Optional;

/**
 * User security operations like login and logout, and CRUD operations on {@link UserSession}.
 *
 * @author jerome
 *
 */
public interface UserSessionService {

    /**
     * called on username & password login
     * @param username
     * @param password
     * @return created UserSession when authentication is successful, empty when login fails
     */
    Optional<UserSession> createSession(String username, String password);

    /**
     * Finds session object by id
     * @param id session id
     * @return UserSession if exists, otherwise empty
     */
    Optional<UserSession> find(String id);

    /**
     * Revokes session on logout
     * @param session UserSession object to revoke
     * @return true if successful, false if fail (for example session doesnt exist)
     */
    boolean revokeSession(UserSession session);

    //currently used only as private method in implementation class
    //Optional<UserSession> findByUsername(String username);
}