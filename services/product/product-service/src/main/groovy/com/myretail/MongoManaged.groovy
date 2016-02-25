package com.myretail

import com.mongodb.Mongo
import io.dropwizard.lifecycle.Managed

/**
 * Class to manage resources on application start and stop
 */
class MongoManaged implements Managed {

    private Mongo mongo

    public MongoManaged(Mongo mongo){
        this.mongo = mongo
    }

    @Override
    public void start() {}

    @Override
    public void stop() {
        mongo.close()
    }
}
