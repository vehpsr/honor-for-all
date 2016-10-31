package com.honor.forall.dao;

import java.util.Set;

import com.honor.forall.model.vm.HeroVm;

public interface HeroDao {

    Set<HeroVm> getHeroes();

}
