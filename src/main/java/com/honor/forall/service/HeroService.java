package com.honor.forall.service;

import java.util.Set;

import com.honor.forall.enums.HeroField;
import com.honor.forall.model.base.Hero;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.model.vm.HeroesVm;

public interface HeroService {

    HeroesVm getHeroes(Set<Integer> heroIds, Set<Hero.Class> heroClasses, Set<HeroField> heroFields);

    HeroVm getHero(int heroId);

}
