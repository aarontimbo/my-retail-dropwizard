package com.myretail.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.Mongo;

/**
 * Health check to determine if MongoDB is alive
 */
class MongoHealthCheck extends HealthCheck {

    private Mongo mongo;

    protected MongoHealthCheck(Mongo mongo) {
        this.mongo = mongo;
    }

    @Override
    protected Result check() throws Exception {
        mongo.getDatabaseNames();
        return Result.healthy();
    }
}
