package com.honor.forall.resources.params;

import java.util.function.Function;

import javax.annotation.Nullable;

public class IntParams extends AbstractCustomParams<Integer> {

    public IntParams(String input) {
        super(input);
    }

    @Override
    @Nullable
    protected Function<String, Integer> converter() {
        return string -> {
            try {
                return Integer.valueOf(string);
            } catch (NumberFormatException e) {
                return null;
            }
        };
    }

    @Override
    protected String errorDetails() {
        return "Expected numeric value.";
    }

}
