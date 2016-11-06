package com.honor.forall.dao.typehandler;

import java.util.function.Function;

import com.honor.forall.model.base.SpellStats;
import com.honor.forall.util.SerializationUtils;

public class SpellStatsTypeHandler extends AbstractTypeHandler<SpellStats> {

    public SpellStatsTypeHandler() {
        super(format(), null);
    }

    private static Function<String, SpellStats> format() {
        return json -> SerializationUtils.fromJson(json, SpellStats.class);
    }

}
