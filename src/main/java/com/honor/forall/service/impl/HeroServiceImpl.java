package com.honor.forall.service.impl;

import java.util.Set;

import com.honor.forall.dao.HeroDao;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.model.vm.HeroesVm;
import com.honor.forall.service.HeroService;

public class HeroServiceImpl implements HeroService {

    private final HeroDao heroDao;

    public HeroServiceImpl(HeroDao heroDao) {
        this.heroDao = heroDao;
    }

    @Override
    public HeroesVm getHeroes() {
        Set<HeroVm> heroes = heroDao.getHeroes();
        return new HeroesVm().withHeroes(heroes);
    }

}
