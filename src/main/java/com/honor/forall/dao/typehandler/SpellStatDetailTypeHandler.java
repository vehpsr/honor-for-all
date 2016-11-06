package com.honor.forall.dao.typehandler;

import java.util.function.Function;

import com.honor.forall.model.base.SpellStatDetail;
import com.honor.forall.util.SerializationUtils;

public class SpellStatDetailTypeHandler extends AbstractTypeHandler<SpellStatDetail> {

    public SpellStatDetailTypeHandler() {
        super(format(), null);
    }

    private static Function<String, SpellStatDetail> format() {
        return json -> SerializationUtils.fromJson(json, SpellStatDetail.class);
    }

}
