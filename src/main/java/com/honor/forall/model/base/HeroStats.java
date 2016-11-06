package com.honor.forall.model.base;

import java.util.HashMap;

@SuppressWarnings("serial")
public class HeroStats extends HashMap<HeroStat, Double> {

    public HeroStats with(HeroStat stat, Double value) {
        put(stat, value);
        return this;
    }
    
    public HeroStats add(HeroStat stat) {
        put(stat, stat.getDefault());
        return this;
    }
}
