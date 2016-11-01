package com.honor.forall.dao.impl;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.honor.forall.dao.HeroDao;
import com.honor.forall.dao.mapper.HeroMapper;
import com.honor.forall.enums.HeroField;
import com.honor.forall.model.base.Hero;
import com.honor.forall.model.db.SpellDb;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.util.ParallelExecutionUtils;

public class HeroDaoImpl implements HeroDao {

    private final SqlSessionFactory sessionFactory;

    public HeroDaoImpl(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<HeroVm> getHeroes(Set<Integer> heroIds, Set<Hero.Class> heroClasses, Set<HeroField> heroFields) {
        if (heroFields.isEmpty()) {
            return Collections.emptySet();
        }
        try (SqlSession session = sessionFactory.openSession(true)) {
            HeroMapper mapper = session.getMapper(HeroMapper.class);

            Supplier<Set<HeroVm>> heroSupplier;
            if (heroFields.contains(HeroField.HERO)) {
                heroSupplier = () -> mapper.getHeroes(heroIds, heroClasses);
            } else {
                heroSupplier = () -> Collections.emptySet();
            }

            Supplier<Set<SpellDb>> spellSupplier;
            if (heroFields.contains(HeroField.SPELLS)) {
                spellSupplier = () -> mapper.getSpells(heroIds, heroClasses);
            } else {
                spellSupplier = () -> Collections.emptySet();
            }

            BiFunction<Set<HeroVm>, Set<SpellDb>, Set<HeroVm>> merge = (heroes, spells) -> {
                Map<Long, HeroVm> heroById = heroes.stream().collect(Collectors.toMap(h -> h.getId(), h -> h));
                Map<Long, List<SpellDb>> spellsByHeroId = spells.stream().collect(Collectors.groupingBy(s -> s.getHeroId()));

                Set<Long> ids = new LinkedHashSet<>();
                ids.addAll(heroById.keySet());
                ids.addAll(spellsByHeroId.keySet());

                Set<HeroVm> result = new LinkedHashSet<>();
                for (Long id : ids) {
                    HeroVm hero = heroById.getOrDefault(id, new HeroVm().withId(id));
                    List<SpellDb> heroSpells = spellsByHeroId.get(id);
                    hero.setSpells(heroSpells == null ? null : new LinkedHashSet<>(heroSpells));
                    result.add(hero);
                }
                return result;
            };

            return ParallelExecutionUtils.execute(merge, heroSupplier, spellSupplier);
        }
    }

}
