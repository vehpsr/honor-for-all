package com.honor.forall.dao.impl;

import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.honor.forall.dao.UserDao;
import com.honor.forall.dao.mapper.UserMapper;
import com.honor.forall.model.base.User;

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
            throw new RuntimeException("Authorization required: guest user is not supported"); // TODO treat like 401 HTTP response
        }
        return guest;
    }
}
