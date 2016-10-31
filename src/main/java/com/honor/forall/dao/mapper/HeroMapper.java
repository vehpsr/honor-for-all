package com.honor.forall.dao.mapper;

import java.util.Set;

import com.honor.forall.dao.dataaccess.HeroDataAccess;

public interface HeroMapper {

    Set<HeroDataAccess> getHeroes();

}
