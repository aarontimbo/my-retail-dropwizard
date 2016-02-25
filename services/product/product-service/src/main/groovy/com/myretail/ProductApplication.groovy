package com.myretail

import com.mongodb.Mongo
import com.myretail.conf.ProductConfiguration
//import com.myretail.health.MongoHealthCheck
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

/**
 * Main class for running the my-retail product application
 */
class ProductApplication extends Application<ProductConfiguration>{

    public static void main(String[] args) throws Exception {
        new ProductApplication().run(args)
    }

    @Override
    public void initialize(Bootstrap<ProductConfiguration> bootstrap) {
        // TODO
    }

    @Override
    public void run(ProductConfiguration configuration, Environment environment) {
        Mongo mongo = new Mongo(configuration.mongohost, configuration.mongoport)
        MongoManaged mongoManaged = new MongoManaged(mongo)
        environment.lifecycle().manage(mongoManaged)

//        environment.healthChecks().register('mongo', new MongoHealthCheck(mongo))

    }

}
