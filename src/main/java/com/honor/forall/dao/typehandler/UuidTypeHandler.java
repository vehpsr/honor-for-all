package com.honor.forall.dao.typehandler;

import java.util.UUID;

public class UuidTypeHandler extends AbstractTypeHandler<UUID> {

    public UuidTypeHandler() {
        super(UUID::fromString, null);
    }

}
