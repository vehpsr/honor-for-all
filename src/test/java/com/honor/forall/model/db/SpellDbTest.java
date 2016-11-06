package com.honor.forall.model.db;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import com.honor.forall.model.base.HeroStat;
import com.honor.forall.model.base.SpellStat;
import com.honor.forall.model.base.SpellStat.Target;
import com.honor.forall.model.base.SpellStatDetail;
import com.honor.forall.util.SerializationUtils;

public class SpellDbTest {

    @Test
    public void testStatDetailLimit() {
        SpellStatDetail statDetail = new SpellStatDetail();

        Set<SpellStat> stats = new LinkedHashSet<>();
        SpellStat stat = new SpellStat();
        stat.setTarget(Target.ENEMY);
        stat.setHeroStat(HeroStat.HP);
        stat.setHitValue(-150.0);
        stats.add(stat);

        stat = new SpellStat();
        stat.setTarget(Target.ALLY);
        stat.setHeroStat(HeroStat.HP);
        stat.setHitValue(150.0);
        stats.add(stat);

        statDetail.setCooldown(10L);
        statDetail.setManacost(1000.0);
        statDetail.setStats(stats);

        String statDetails = SerializationUtils.toJson(statDetail);
        assertThat(statDetails.length(), lessThan(512));
    }
}
