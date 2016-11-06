package com.honor.forall.model.db;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.honor.forall.model.base.HeroStat;
import com.honor.forall.model.base.SpellStat;
import com.honor.forall.model.base.SpellStats;
import com.honor.forall.model.base.SpellStat.Target;
import com.honor.forall.util.SerializationUtils;

public class SpellDbTest {

    @Test
    public void testStatDetailLimit() {
        SpellStats stats = new SpellStats();

        SpellStat stat = new SpellStat();
        stat.setTarget(Target.ENEMY);
        stat.setHeroStat(HeroStat.HP);
        stat.setHitValue(-150.0);
        stats.add(stat);

        String statDetails = SerializationUtils.toJson(stats);
        System.out.println(statDetails);
        assertThat(statDetails.length(), lessThan(512));
    }
}
