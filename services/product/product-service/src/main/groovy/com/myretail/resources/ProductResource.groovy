package com.myretail.resources

import com.codahale.metrics.annotation.Timed
import com.myretail.domain.ProductEntity
import com.myretail.modules.ProductModule
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import com.wordnik.swagger.annotations.ApiParam
import com.wordnik.swagger.annotations.ApiResponse
import com.wordnik.swagger.annotations.ApiResponses

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
@Api(value = "/products", description = "Product data access")
@Produces(MediaType.APPLICATION_JSON)
class ProductResource {

    private ProductModule productModule

    ProductResource(ProductModule productModule) {
        this.productModule = productModule
    }

    @Path('/{productId}')
    @GET
    @Timed
    @ApiOperation(
            value = "Find pet by Product ID",
            notes = "Returns a product",
            response = ProductEntity)
    @ApiResponses(value = [ @ApiResponse(code = 404, message = "No product found for ID:") ] )
    ProductEntity findProduct(@ApiParam(value = "ID of product to be fetched", required = true)
                              @PathParam('productId') Long productId) {
        
        ProductEntity productEntity = productModule.getProduct(productId)

        if (!productEntity) {
            throw new NotFoundException("No product found for ID: ${productId}")
        }
        return productEntity
    }
    
}
