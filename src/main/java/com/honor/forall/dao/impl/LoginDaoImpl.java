package com.honor.forall.dao.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.honor.forall.dao.LoginDao;
import com.honor.forall.dao.UserDao;
import com.honor.forall.dao.mapper.LoginMapper;
import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;
import com.honor.forall.model.db.AuthTokenDb;

public class LoginDaoImpl implements LoginDao {

    private final SqlSessionFactory sessionFactory;
    private final UserDao userDao;

    public LoginDaoImpl(SqlSessionFactory sessionFactory, UserDao userDao) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
    }

    @Override
    public AuthTokenDb guestAuthToken() {
        AuthTokenDb token = newAuthToken();
        try (SqlSession session = sessionFactory.openSession(true)) {
            LoginMapper mapper = session.getMapper(LoginMapper.class);
            User guest = userDao.guestUser();
            token.setUserId(guest.getId());
            mapper.addToken(token);
        }
        return token;
    }

    private static AuthTokenDb newAuthToken() {
        AuthTokenDb token = new AuthTokenDb();
        token.setToken(UUID.randomUUID());
        token.setType(AuthToken.Type.BEARER);
        token.setUpdated(new Date());
        return token;
    }
}
