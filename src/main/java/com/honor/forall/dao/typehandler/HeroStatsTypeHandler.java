package com.honor.forall.dao.typehandler;

import java.util.function.Function;

import org.apache.ibatis.type.MappedTypes;

import com.honor.forall.model.base.Stats;
import com.honor.forall.util.SerializationUtils;

@MappedTypes(Stats.class)
public class HeroStatsTypeHandler extends AbstractTypeHandler<Stats> {

    public HeroStatsTypeHandler() {
        super(format(), null);
    }

    private static Function<String, Stats> format() {
        return json -> SerializationUtils.fromJson(json, Stats.class);
    }

}
