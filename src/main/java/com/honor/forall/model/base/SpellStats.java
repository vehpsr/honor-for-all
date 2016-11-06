package com.honor.forall.model.base;

import java.util.HashSet;

@SuppressWarnings("serial")
public class SpellStats extends HashSet<SpellStat> {

    public SpellStats with(SpellStat stat) {
        add(stat);
        return this;
    }
}
