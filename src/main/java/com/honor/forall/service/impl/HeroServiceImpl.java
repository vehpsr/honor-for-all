package com.honor.forall.service.impl;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javax.annotation.Nullable;

import com.honor.forall.dao.HeroDao;
import com.honor.forall.enums.HeroField;
import com.honor.forall.model.base.Hero;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.model.vm.HeroesVm;
import com.honor.forall.service.HeroService;

public class HeroServiceImpl implements HeroService {

    private final HeroDao heroDao;

    public HeroServiceImpl(HeroDao heroDao) {
        this.heroDao = heroDao;
    }

    @Override
    public HeroesVm getHeroes(Set<Integer> heroIds, Set<Hero.Class> heroClasses, Set<HeroField> heroFields) {
        Set<HeroVm> heroes = heroDao.getHeroes(heroIds, heroClasses, heroFields);
        return new HeroesVm().withHeroes(heroes);
    }

    @Override
    @Nullable
    public HeroVm getHero(int heroId) {
        HeroesVm heroes = getHeroes(Collections.singleton(heroId), Collections.emptySet(), EnumSet.allOf(HeroField.class));
        if (heroes.getHeroes().isEmpty()) {
            return null;
        } else {
            return heroes.getHeroes().iterator().next();
        }
    }

}
