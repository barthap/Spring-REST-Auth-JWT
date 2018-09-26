# Spring REST JWT Token Auth boilerplate

*This repository is something like proof of concept,
and probably a good boilerplate for further projects. It was written to practise and possibly
to use it in future with some modifications.*

This is an implementation of custom JWT token-based authentication
for Spring Boot 2 and Spring Security 5. It doesn't use any built-in
Spring Security authentication features
like form login, httpBasic etc. Most of the logic is written explicitly by me
and is just connected to Spring Security flow to provide some useful features.

## Example usage

Build and run the project.
Starts by default at `http://localhost:8080`.

##### Register
To register a new user, call
```
curl -XPOST -d 'username=john&password=smith' http://localhost:8080/public/users/register
```
And the example response will be:
`eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAB3LSwqAIBAA0LvMOsH_ON2gY2QquElpXATR3ftsH7wLKjPM0LbRej4KTFDXAbNyBsl6lG6CfPYfgjRGfcCZubZ9SW8sMflVexIKkYQt0omAHkUMScdCJFMwcD_dkSG-ZwAAAA.zO2ph4z28Z85E3af9ad_gd-wWYUZiRf7_bVvE1x6zt4`
which is a JWT token.
Application automatically logs in a session when registering.


##### Login
To login already registered user, simply call
```
curl -XPOST -d 'username=john&password=smith' http://localhost:8080/public/users/login
```

The response will also be a session token.

##### Get current user info
Call this API endpoint, sending token in `Authorization` header:
```
curl -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6Ik...' http://localhost:8080/users/current
```
This will return
- `anonymousUser`, when `Authorization` header is not specified
- `401 Unauthorized` response, when token is invalid or session expired 
- JSON representation of `UserSession` class:
```json
{
    "sessionId": "fbd6a269-1779-4f05-8767-b8d2bf990d83",
    "user": {
        "id": 1,
        "username": "john",
        "roles": [
            "ROLE_USER"
        ]
    },
    "createdTimestamp": "Tue Sep 25 16:24:49 UTC 2018",
    "expiryDurationSecs": 0,
    "accountNonExpired": true,
    "authorities": [
        {
            "authority": "ROLE_USER"
        }
    ],
    "enabled": true
}
```

##### Protected API calls
Example protected endpoint:
```
curl -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6Ik...' http://localhost:8080/users/priv
```
Will return:
- `403 Forbidden` when no Authorization header is provided
- `401 Unauthorized` when token or session is invalid
- `200 OK` with some text response, if everything is OK and user session has `ROLE_USER` authority

## How it works
It was based on [this](https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/) article,
but was deeply modified by me. There is a brief description of the code, grouped by package names

##### `security.config`
Spring Security configuration is defined in `SecurityConfig` class.
Endpoints defined as `PUBLIC_URLS` have no security, any other endpoint is secured
by Spring `Global method security`.
The job is done by `TokenAuthenticationFilter` custom chain filter.
It is placed manually just before `AnonymousAuthenticationFilter`, so when there is no token in request header,
the chain continues to work and authenticates as `AnonymousAuthenticationToken`. The filter authenticates token
using `TokenAuthenticationProvider`. All exceptions during authentication process
are caught by `RestAuthenticationFailureHandler` where custom `401` response is sent.

##### `security.auth`
The core of auth logic is here. The most top-level service used by either controllers and Spring's
authentication provider is `UserAuthenticationService` interface.
It defines method for login (start a new session and create token),
Retrieving session info by token or revoking session (logout). It is implemented in `TokenAuthenticationService` class.
It connects decoded JWT token info with stored user sessions.

`UserRegistrationService` is simple interface to register new users. It has nothing special, no validation,
just basic saving new users to repository and giving them `ROLE_USER` by default.

`UserSession` is an implementation of Spring Security `UserInfo` interface. It provides basic user information
and current session details and some methods needed by Spring. It is created when user logs in and provided
as `@AuthenticationPrincipal` to controllers. It is managed by `UserSessionService` interface and it's
`InMemoryUserSessionService` implementation, which stores all active sessions in simple `HashMap`. The service
is currently only used in `TokenAuthenticationService` to manage sessions.

##### `security.token`
Contains `TokenService` interface and its `JWTTokenService` implementation. It provides methods for token-specific
operations, like creating and signing new tokens and verifying existing. It is used in `TokenAuthenticationService`.

##### `controller`
Contains REST controllers. One is for *public* operations like login and register, and other for everything else.

##### `data`
Currently contains only User model and repository with simple in-memory implementation. It can be simply replaced with
Spring Data models and repositories.

### TODO:
- Add more unit and integration tests
- Convert code to Kotlin? Seems to be a nice language and works well with Spring
