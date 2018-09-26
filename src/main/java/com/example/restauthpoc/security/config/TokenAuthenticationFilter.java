package com.example.restauthpoc.security.config;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
import lombok.experimental.FieldDefaults;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@CommonsLog
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String BEARER = "Bearer";
    private static final String NO_TOKEN = "noToken";

    TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    /* NOTE: We explicitly do not override doFilter method, but only attemptAuthentication() and authenticationSuccess()
     * just to overwrite already existing super class code
     */
    //TODO: think about rewriting it anyway (see details below)
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response) {
        final String param = ofNullable(request.getHeader(AUTHORIZATION))
                .orElse(request.getParameter("token"));

        String token = ofNullable(param)
                .map(value -> removeStart(value, BEARER))
                .map(String::trim).orElse(NO_TOKEN);
                //.orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));

        /**
         * NOTE: if there is no token, we create a fake dummy Authentication object,
         * then we gather it in successfullAuthentication(). We cannot just return null, because
         * it would break the filter chain and we definitely don't want it.
         * @see AbstractAuthenticationProcessingFilter::doFilter()
         */
        if(NO_TOKEN.equals(token))return new PreAuthenticatedAuthenticationToken(NO_TOKEN, NO_TOKEN);

            log.info("Token filter worked, " + token);
            final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
            return getAuthenticationManager().authenticate(auth); //this makes TokenAuthenticationProvider::retrieveUser() call happen

    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {

        //checks for our special authentication object
        if(!authResult.getPrincipal().equals(NO_TOKEN) || !authResult.getCredentials().equals(NO_TOKEN)) {

            //This updates Security context with new authentication object
            //We don't want to update the context with our fake authentication
            super.successfulAuthentication(request, response, chain, authResult);
        }

        // regardless of which authentication object was returned by attemptAuthentication(),
        // continue filter chain
        chain.doFilter(request, response);
    }
}