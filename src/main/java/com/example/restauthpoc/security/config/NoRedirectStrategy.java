package com.example.restauthpoc.security.config;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class NoRedirectStrategy implements RedirectStrategy {

    @Override
    public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url) throws IOException {
        // No redirect is required with pure REST
    }
}

