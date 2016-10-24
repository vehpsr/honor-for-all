package com.honor.forall.health;

import com.codahale.metrics.health.HealthCheck;

public class HonorForAllHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

}
