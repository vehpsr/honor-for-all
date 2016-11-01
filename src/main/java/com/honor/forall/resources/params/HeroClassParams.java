package com.honor.forall.resources.params;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.honor.forall.model.base.Hero;
import com.honor.forall.model.base.Hero.Class;

public class HeroClassParams extends AbstractCustomParams<Hero.Class> {

    private static final Map<String, Hero.Class> CLASSES =
            Stream.of(Hero.Class.values()).collect(Collectors.toMap(k -> k.name().toLowerCase(), v -> v));

    public HeroClassParams(String input) {
        super(input);
    }

    @Override
    @Nullable
    protected Function<String, Class> converter() {
        return string -> CLASSES.get(string);
    }

    @Override
    protected String errorDetails() {
        return String.format("Expected one of %s", CLASSES.keySet());
    }

}
