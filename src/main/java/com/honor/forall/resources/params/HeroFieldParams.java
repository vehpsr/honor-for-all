package com.honor.forall.resources.params;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.honor.forall.enums.HeroField;

public class HeroFieldParams extends AbstractCustomParams<HeroField> {

    private static final Map<String, HeroField> FIELDS =
            Stream.of(HeroField.values()).collect(Collectors.toMap(k -> k.name().toLowerCase(), v -> v));

    public HeroFieldParams(String input) {
        super(input);
    }

    @Override
    @Nullable
    protected Function<String, HeroField> converter() {
        return string -> FIELDS.get(string);
    }

    @Override
    protected String errorDetails() {
        return String.format("Expected one of %s", FIELDS.keySet());
    }
}
