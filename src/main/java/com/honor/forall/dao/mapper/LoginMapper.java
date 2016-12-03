package com.honor.forall.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.honor.forall.model.db.AuthTokenDb;

public interface LoginMapper {

    void addToken(@Param("token") AuthTokenDb token);

}
