package com.honor.forall.core.security;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;
import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;
import com.honor.forall.service.UserService;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class HonorOAuthAuthenticator implements Authenticator<String, User> {

    private final UserService userService;

    public HonorOAuthAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(String bearerToken) throws AuthenticationException {
        if (StringUtils.isEmpty(bearerToken)) {
            throw new AuthenticationException("Bearer token required and can't be empty");
        }
        UUID token;
        try {
            token = UUID.fromString(bearerToken);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid bearer token format", e);
        }
        AuthToken authToken = new AuthToken().withToken(token).withType(AuthToken.Type.BEARER);
        User user = userService.getUser(authToken);
        return Optional.fromNullable(user);
    }

}
