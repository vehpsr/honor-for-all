package com.honor.forall.core.security;

import com.honor.forall.model.base.User;

import io.dropwizard.auth.Authorizer;

public class HonorAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        return user != null;
    }

}
