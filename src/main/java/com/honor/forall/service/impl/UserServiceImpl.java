package com.honor.forall.service.impl;

import com.honor.forall.dao.UserDao;
import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;
import com.honor.forall.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public AuthToken guestAuthToken() {
        return userDao.guestAuthToken();
    }

    @Override
    public User getUser(AuthToken authToken) {
        return userDao.getUser(authToken);
    }

}
