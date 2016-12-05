package com.honor.forall.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.honor.forall.model.base.AuthToken;
import com.honor.forall.model.base.User;
import com.honor.forall.model.db.AuthTokenDb;

public interface UserMapper {

    User guestUser();

    User getUserByToken(@Param("token") AuthToken authToken);

    void addToken(@Param("token") AuthTokenDb token);
}
