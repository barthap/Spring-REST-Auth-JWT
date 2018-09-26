package com.example.restauthpoc.security.auth;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
import com.example.restauthpoc.data.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
final class InMemoryUserSessionService implements UserSessionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    private Map<String, UserSession> sessions = new HashMap<>();

    @Override
    public Optional<UserSession> createSession(String username, String password) {

        //check if session already exists
        //TODO: This allows only ONE SESSION PER USERNAME
        val existing = findByUsername(username);
        if(existing.isPresent())
            return existing;

        Optional<UserSession> session = userRepository
                .findByUsername(username)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .map(u -> UserSession.builder()
                    .sessionId(UUID.randomUUID().toString())
                    .user(u).expiryDurationSecs(0).build()
                );

        session.ifPresent(s -> sessions.put(s.getSessionId(), s));

        return session;
    }

    @Override
    public Optional<UserSession> find(final String id) {
        return ofNullable(sessions.get(id));
    }

    @Override
    public boolean revokeSession(UserSession session) {
        return ofNullable(sessions.remove(session.getSessionId())).isPresent();
    }

    //This could be public if needed
    private Optional<UserSession> findByUsername(final String username) {
        return sessions
                .values()
                .stream()
                .filter(s -> Objects.equals(username, s.getUsername()))
                .findFirst();
    }
}