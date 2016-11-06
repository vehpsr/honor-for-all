package com.honor.forall.model.db;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.honor.forall.model.base.HeroStat;
import com.honor.forall.model.base.HeroStats;
import com.honor.forall.util.SerializationUtils;

public class HeroDbTest {

    @Test
    public void testStatDetailsLimit() throws Exception {
        HeroStats stats = new HeroStats();
        for (HeroStat stat : HeroStat.BASE_STATS) {
            stats.put(stat, 10000.0);
        }
        String statDetails = SerializationUtils.toJson(stats);
        assertThat(statDetails.length(), lessThan(256)); // HeroDb statDetails column size constraint
    }
}
