package com.honor.forall.model.base;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Stats extends HashMap<Stat, Double> {

    public Stats with(Stat stat, Double value) {
        put(stat, value);
        return this;
    }
}
