package com.honor.forall.resources.params;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import com.honor.forall.exception.response.ErrorResponse;

public abstract class AbstractCustomParams<T> extends AbstractCustomParam<Set<T>> {

    protected AbstractCustomParams(String input) {
        super(input);
    }

    protected abstract Function<String, T> converter();

    @Override
    protected Set<T> parse(String input) throws Exception {
        if (StringUtils.isBlank(input)) {
            return Collections.emptySet();
        }

        Set<String> errors = new HashSet<>();
        Set<T> fields = new HashSet<>();
        for (String inField : input.split(",")) {
            if (StringUtils.isBlank(inField)) {
                continue;
            }

            T field = converter().apply(inField.trim().toLowerCase());
            if (field != null) {
                fields.add(field);
            } else {
                errors.add(inField);
            }
        }

        if (!errors.isEmpty()) {
            invalidInputError(errors);
        }

        return fields;
    }

    public Optional<T> single() {
        Set<T> params = get();
        if (params.isEmpty()) {
            return Optional.empty();
        } else if (params.size() > 1) {
            throw badRequest("Single value expected, got " + params);
        } else {
            return Optional.of(params.iterator().next());
        }
    }

    protected void invalidInputError(Set<String> errors) {
        String errorMsg = String.format("Invalid input values %s.", errors);
        String customErrorMsg = errorDetails();
        if (StringUtils.isNotEmpty(customErrorMsg)) {
            errorMsg = String.format("%s %s", errorMsg, customErrorMsg);
        }
        throw badRequest(errorMsg);
    }

    protected String errorDetails() {
        return "";
    }

    protected static WebApplicationException badRequest(String message) {
        Response response = ErrorResponse.build(HttpStatus.BAD_REQUEST_400, message);
        return new WebApplicationException(response);
    }
}
