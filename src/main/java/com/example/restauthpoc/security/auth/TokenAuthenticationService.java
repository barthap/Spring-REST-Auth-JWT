package com.example.restauthpoc.security.auth;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
import com.example.restauthpoc.security.token.TokenService;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationService implements UserAuthenticationService {
    @NonNull
    TokenService tokenService;
    @NonNull
    UserSessionService sessionService;

    private static final String SESSION_ID = "sessionId";

    @Override
    //called when login
    public Optional<String> login(final String username, final String password) {

        //currently this service only creates JWT token containing Session ID
        //In future token would store also information about client device or sth.
        return sessionService
                .createSession(username, password)
                .map(
                        session -> tokenService.createExpiring(ImmutableMap.of(SESSION_ID, session.getSessionId()))
                );
    }

    @Override
    public Optional<UserSession> findByToken(final String token) {

        return Optional
                .of(tokenService.verify(token))
                .map(map -> map.get(SESSION_ID))
                .flatMap(sessionService::find);
    }

    @Override
    public void logout(final UserSession user) {
        sessionService.revokeSession(user);
    }
}