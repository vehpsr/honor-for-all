package com.honor.forall.service;

import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;

public interface UserService {

    AuthToken guestAuthToken();

    User getUser(AuthToken authToken);

}
