package com.honor.forall.dao.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.honor.forall.dao.HeroDao;
import com.honor.forall.dao.dataaccess.HeroDataAccess;
import com.honor.forall.dao.mapper.HeroMapper;
import com.honor.forall.model.vm.HeroVm;

public class HeroDaoImpl implements HeroDao {

    private final SqlSessionFactory sessionFactory;

    public HeroDaoImpl(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<HeroVm> getHeroes() {
        try (SqlSession session = sessionFactory.openSession(true)) {
            HeroMapper mapper = session.getMapper(HeroMapper.class);
            Set<HeroDataAccess> data = mapper.getHeroes();
            Set<HeroVm> heroes = data.stream().map(h -> h.convert()).collect(Collectors.toSet());
            return heroes;
        }
    }
}
