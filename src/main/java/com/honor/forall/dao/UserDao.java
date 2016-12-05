package com.honor.forall.dao;

import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;
import com.honor.forall.model.db.AuthTokenDb;

public interface UserDao {

    User guestUser();

    User getUser(AuthToken authToken);

    AuthTokenDb guestAuthToken();
}
