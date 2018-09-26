package com.example.restauthpoc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * Created by barthap on 26.09.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestServiceTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    public void unauthorizedAccessProtectedResourceTest() {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/priv"),
                HttpMethod.GET,
                entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);

    }

    @Test
    public void wrongTokenAccessProtectedResourceTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer invalid");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/priv"),
                HttpMethod.GET,
                entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);

    }

    @Test
    public void createUserTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>("username=user&password=pass", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/public/users/register"),
                HttpMethod.POST,
                entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertTrue(response.getBody().startsWith("ey"));    //all JWT tokens start with 'ey' (its base64 of {" chars)
    }

    @Test
    public void anonymousCurrentUserInfoTest() {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/current"),
                HttpMethod.GET,
                entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "anonymousUser");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
