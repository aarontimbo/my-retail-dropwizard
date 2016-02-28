package com.myretail

import com.mongodb.DB
import com.mongodb.Mongo
import com.myretail.conf.ProductConfiguration
import com.myretail.consumers.ProductDetailConsumer
import com.myretail.consumers.ProductDetailConsumerImpl
import com.myretail.dao.ProductPriceDAO
import com.myretail.dao.ProductPriceDAOImpl
import com.myretail.domain.ProductPriceEntity
import com.myretail.health.MongoHealthCheck
import com.myretail.modules.ProductModule
import com.myretail.resources.ProductResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import net.vz.mongodb.jackson.JacksonDBCollection

/**
 * Main class for running the my-retail product application
 */
class ProductApplication extends Application<ProductConfiguration>{

    public static void main(String[] args) throws Exception {
        new ProductApplication().run(args)
    }

    @Override
    public void initialize(Bootstrap<ProductConfiguration> bootstrap) {
        // not yet implemented
    }

    @Override
    public void run(ProductConfiguration configuration, Environment environment) {
        Mongo mongo = new Mongo(configuration.mongohost, configuration.mongoport)
        MongoManaged mongoManaged = new MongoManaged(mongo)
        environment.lifecycle().manage(mongoManaged)

        environment.healthChecks().register('mongo', new MongoHealthCheck(mongo))

        ProductDetailConsumer productDetailConsumer = new ProductDetailConsumerImpl()

        DB db = mongo.getDB(configuration.mongodb)
        JacksonDBCollection<ProductPriceEntity, String> productPricesCollection = getCollection(configuration, db)
        ProductPriceDAO productPriceDAO = new ProductPriceDAOImpl(productPricesCollection)

        ProductModule productModule = new ProductModule(productPriceDAO, productDetailConsumer)

        ProductResource productResource = new ProductResource(productModule)
        environment.jersey().register(productResource)
    }

    private JacksonDBCollection<ProductPriceEntity, String> getCollection(ProductConfiguration configuration, DB db) {
        return JacksonDBCollection.wrap(db.getCollection(configuration.collectionName), ProductPriceEntity, String)
    }

}
