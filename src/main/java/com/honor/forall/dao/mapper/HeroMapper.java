package com.honor.forall.dao.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.honor.forall.model.base.Hero;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.model.vm.SpellVm;

public interface HeroMapper {

    Set<HeroVm> getHeroes(
            @Param("ids") Set<Integer> heroIds,
            @Param("classes") Set<Hero.Class> heroClasses
        );

    Set<SpellVm> getSpells(
            @Param("ids") Set<Integer> heroIds,
            @Param("classes") Set<Hero.Class> heroClasses
        );

}
