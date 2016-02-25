package com.myretail

import com.myretail.conf.ProductConfiguration
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
        // TODO

    }

}
