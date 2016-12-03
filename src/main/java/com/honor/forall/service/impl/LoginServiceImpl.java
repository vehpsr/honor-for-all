package com.honor.forall.service.impl;

import com.honor.forall.dao.LoginDao;
import com.honor.forall.model.base.AuthToken;
import com.honor.forall.service.LoginService;

public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;

    public LoginServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public AuthToken guestAuthToken() {
        return loginDao.guestAuthToken();
    }

}
