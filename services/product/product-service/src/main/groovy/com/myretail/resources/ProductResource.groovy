package com.myretail.resources

import com.codahale.metrics.annotation.Timed
import com.myretail.domain.ProductEntity
import com.myretail.modules.ProductModule

import javax.ws.rs.GET
import javax.ws.rs.NotFoundException
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Resource with endpoints for accessing Product data
 */
@Path('/products')
@Produces(MediaType.APPLICATION_JSON)
class ProductResource {

    private ProductModule productModule

    ProductResource(ProductModule productModule) {
        this.productModule = productModule
    }

    @Path('/{productId}')
    @GET
    @Timed
    ProductEntity findProduct(@PathParam('productId') Long productId) {
        ProductEntity productEntity = productModule.getProduct(productId)

        if (!productEntity) {
            throw new NotFoundException("No product found for ID: ${productId}")
        }
        return productEntity
    }
}
