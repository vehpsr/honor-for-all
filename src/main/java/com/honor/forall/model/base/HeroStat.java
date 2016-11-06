package com.honor.forall.model.base;

import static com.honor.forall.model.base.HeroStatType.BASE;
import static com.honor.forall.model.base.HeroStatType.SECONDARY;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HeroStat {

    // base
    HP("HP", BASE, 1000),
    HP_REGEN("hpRegen", BASE, 15),
    MP("MP", BASE, 500),
    MP_REGEN("mpRegen", BASE, 10),
    DAMAGE("damage", BASE, 100),
    CRIT_CHANCE("critChance", BASE, 0.1), // %
    CRIT_DAMAGE("critDamage", BASE, 1.1), // %
    EVADE("evade", BASE, 0.1), // %
    BLOCK("block", BASE, 10),
    ARMOR("armor", BASE, 100),
    RESISTANCE("resistance", BASE, 0), // %
    SPELL_POWER("spellPower", BASE, 1), // %
    ATTACK_POWER("attackPower", BASE, 1), // %
    
    // secondary
    SHIELD("shield", SECONDARY, 0),
    LIFE_STEAL("lifeSteal", SECONDARY, 0);
    
    private final String name;
    private final HeroStatType type;
    private final double _default; // default stat value
    
    public static final Set<HeroStat> ALL = EnumSet.allOf(HeroStat.class);
    public static final Set<HeroStat> BASE_STATS = ALL.stream().filter(s -> s.type == BASE).collect(Collectors.toSet());
    
    private final static Map<String, HeroStat> CONSTANTS = new HashMap<>();
    static {
        for (HeroStat c: values()) {
            CONSTANTS.put(c.name, c);
        }
    }

    private HeroStat(String name, HeroStatType type, double _default) {
        this.name = name;
        this.type = type;
        this._default = _default;
    }
    
    public HeroStatType getType() {
        return type;
    }

    public double getDefault() {
        return _default;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.name;
    }

    @JsonCreator
    public static HeroStat fromValue(String value) {
        HeroStat constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
