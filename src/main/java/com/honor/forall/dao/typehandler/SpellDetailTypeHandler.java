package com.honor.forall.dao.typehandler;

import java.util.function.Function;

import com.honor.forall.model.base.SpellDetail;
import com.honor.forall.util.SerializationUtils;

public class SpellDetailTypeHandler extends AbstractTypeHandler<SpellDetail> {

    public SpellDetailTypeHandler() {
        super(format(), null);
    }

    private static Function<String, SpellDetail> format() {
        return json -> SerializationUtils.fromJson(json, SpellDetail.class);
    }

}
