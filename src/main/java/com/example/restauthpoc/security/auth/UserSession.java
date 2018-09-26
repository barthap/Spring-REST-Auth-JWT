package com.example.restauthpoc.security.auth;

/**
 * Created by barthap on 23.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */

import com.example.restauthpoc.data.User;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class UserSession implements UserDetails {
    private static final long serialVersionUID = 2396654715019746670L;

    String sessionId;
    User user;
    final DateTime createdTimestamp = DateTime.now();
    int expiryDurationSecs;

    UserSession(final String sessionId,
         final User user, final int expiryDurationSecs) {
        super();
        this.sessionId = requireNonNull(sessionId);
        this.user = requireNonNull(user);
        this.expiryDurationSecs = expiryDurationSecs;
    }

    @JsonGetter
    public String getCreatedTimestamp() {
        return createdTimestamp.toDate().toString();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities(user.getRoles());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {

        if(expiryDurationSecs == 0)return true;

        return createdTimestamp.plusSeconds(expiryDurationSecs).isAfterNow();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}