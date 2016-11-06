package com.honor.forall.model.db;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import com.honor.forall.model.base.HeroStat;
import com.honor.forall.model.base.SpellTrait;
import com.honor.forall.model.base.SpellTrait.Target;
import com.honor.forall.model.base.SpellDetail;
import com.honor.forall.util.SerializationUtils;

public class SpellDbTest {

    @Test
    public void testStatDetailLimit() {
        SpellDetail spellDetail = new SpellDetail();

        Set<SpellTrait> traits = new LinkedHashSet<>();
        SpellTrait trait = new SpellTrait();
        trait.setTarget(Target.ENEMY);
        trait.setHeroStat(HeroStat.HP);
        trait.setHitValue(-150.0);
        traits.add(trait);

        trait = new SpellTrait();
        trait.setTarget(Target.ALLY);
        trait.setHeroStat(HeroStat.HP);
        trait.setHitValue(150.0);
        traits.add(trait);

        spellDetail.setCooldown(10L);
        spellDetail.setManacost(1000.0);
        spellDetail.setTraits(traits);

        String statDetails = SerializationUtils.toJson(spellDetail);
        assertThat(statDetails.length(), lessThan(512));
    }
}
