package com.honor.forall.dao.typehandler;

import java.util.function.Function;

import org.apache.ibatis.type.MappedTypes;

import com.honor.forall.model.base.HeroStat;
import com.honor.forall.model.base.HeroStats;
import com.honor.forall.util.SerializationUtils;

@MappedTypes(HeroStats.class)
public class HeroStatsTypeHandler extends AbstractTypeHandler<HeroStats> {

    public HeroStatsTypeHandler() {
        super(format(), null);
    }

    private static Function<String, HeroStats> format() {
        return json -> {
            HeroStats stats = SerializationUtils.fromJson(json, HeroStats.class);
            for (HeroStat stat : HeroStat.BASE_STATS) {
                if (!stats.containsKey(stat)) {
                    stats.add(stat);
                }
            }
            return stats;
        };
    }

}
