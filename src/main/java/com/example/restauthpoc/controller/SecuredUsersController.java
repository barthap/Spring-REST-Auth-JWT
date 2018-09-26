package com.example.restauthpoc.controller;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
import com.example.restauthpoc.security.auth.UserAuthenticationService;
import com.example.restauthpoc.security.auth.UserSession;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
class SecuredUsersController {
    @NonNull
    UserAuthenticationService authentication;

    //This just returns current Principal object
    //It is instance of:
    // - UserSession, when user is logged in (token is sent as a header)
    // - String "anonymousUser", when the token is not sent (anonymous REST API call)
    @GetMapping("/current")
    Object getCurrent(@AuthenticationPrincipal final Object principal) {
        return principal;
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final UserSession user) {
        authentication.logout(user);
        return true;
    }

    //Resource available to both anonymous and logged in users
    @GetMapping("/publ")
    String publ() {
        return "Test resource public";
    }

    //Only for users with ROLE_USER (given authomatically during registration)
    //Non logged in users have ROLE_ANONYMOUS by default
    @GetMapping("/priv")
    @PreAuthorize("hasRole('ROLE_USER')")
    String priv() {
        return "Test resource private";
    }
}