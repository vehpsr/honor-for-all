package com.honor.forall.dao.impl;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.honor.forall.dao.UserDao;
import com.honor.forall.dao.mapper.UserMapper;
import com.honor.forall.exception.HonorAuthenticationException;
import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;
import com.honor.forall.model.db.AuthTokenDb;

public class UserDaoImpl implements UserDao {

    private final Supplier<User> guestCache;
    private final SqlSessionFactory sessionFactory;

    public UserDaoImpl(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.guestCache = Suppliers.memoizeWithExpiration(() -> guest(), 30, TimeUnit.MINUTES);
    }

    private User guest() {
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.guestUser();
        }
    }

    @Override
    public User guestUser() {
        User guest = guestCache.get();
        if (guest == null) {
            throw new HonorAuthenticationException("Guest user is not supported");
        }
        return guest;
    }

    @Override
    public User getUser(AuthToken authToken) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getUserByToken(authToken);
        }
    }

    @Override
    public AuthTokenDb guestAuthToken() {
        AuthTokenDb token = newAuthToken();
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User guest = guestUser();
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
