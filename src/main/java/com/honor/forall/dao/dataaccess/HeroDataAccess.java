package com.honor.forall.dao.dataaccess;

import com.honor.forall.model.base.Stats;
import com.honor.forall.model.vm.HeroVm;
import com.honor.forall.util.SerializationUtils;

public class HeroDataAccess implements DataAccess<HeroVm> {

    private Long id;
    private String statDetails;
    private HeroVm hero;

    @Override
    public HeroVm convert() { // TODO custom type-handler?
        Stats stats = SerializationUtils.fromJson(statDetails, Stats.class);
        hero.setStats(stats);
        hero.setSpells(null);
        return hero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatDetails() {
        return statDetails;
    }

    public void setStatDetails(String statDetails) {
        this.statDetails = statDetails;
    }

    public HeroVm getHero() {
        return hero;
    }

    public void setHero(HeroVm hero) {
        this.hero = hero;
    }

}
