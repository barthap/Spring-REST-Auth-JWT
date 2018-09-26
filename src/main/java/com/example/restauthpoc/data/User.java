package com.example.restauthpoc.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by barthap on 25.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */


/**
 * This can be Spring Data entity, stored in DB
 */
@Data
@Builder
public class User {

    private Long id;

    private String username;

    @JsonIgnore //don't show password hash in any REST response
    private String password;

    private List<String> roles;
}
