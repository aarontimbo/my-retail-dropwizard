package com.myretail

import com.mongodb.DB
import com.mongodb.Mongo
import com.myretail.conf.ProductConfiguration
import com.myretail.consumers.ProductDetailConsumer
import com.myretail.consumers.ProductDetailConsumerImpl
import com.myretail.dao.ProductPriceDAO
import com.myretail.dao.ProductPriceDAOImpl
import com.myretail.health.MongoHealthCheck
import com.myretail.modules.ProductModule
import com.myretail.resources.ProductResource
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

        environment.healthChecks().register('mongo', new MongoHealthCheck(mongo))

        DB db = mongo.getDB(configuration.mongodb)
        ProductDetailConsumer productDetailConsumer = new ProductDetailConsumerImpl()
        ProductPriceDAO productPriceDAO = new ProductPriceDAOImpl(db)
        ProductModule productModule = new ProductModule(productPriceDAO, productDetailConsumer)

        ProductResource productResource = new ProductResource(productModule)
        environment.jersey().register(productResource)
    }

}
