package com.myretail.resources

import com.codahale.metrics.annotation.Timed
import com.myretail.domain.ProductEntity
import com.myretail.domain.ProductPriceEntity
import com.myretail.modules.ProductModule
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import com.wordnik.swagger.annotations.ApiParam
import com.wordnik.swagger.annotations.ApiResponse
import com.wordnik.swagger.annotations.ApiResponses

import javax.validation.Valid
import javax.ws.rs.BadRequestException
import javax.ws.rs.GET
import javax.ws.rs.NotFoundException
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Resource for interacting with Product data
 */
@Path('/products')
@Api(value = "/products", description = "Product data access")
@Produces(MediaType.APPLICATION_JSON)
class ProductResource {

    private ProductModule productModule

    ProductResource(ProductModule productModule) {
        this.productModule = productModule
    }

    /*
     * Retrieve a product object by an ID.
     *
     * @param productId  id of the product to retrieve
     * @return  Product object
     */
    @Path('/{productId}')
    @GET
    @Timed
    @ApiOperation(
            value = "Find product by ID",
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

    /*
     * Update an existing product price object.
     *
     * @param productPriceEntity  updated product price object 
     * @return  Response object
     */
    @PUT
    @Timed
    @ApiOperation(
            value = "Update an existing product price",
            notes = "Returns a product",
            response = Response)
    @ApiResponses(value = [ @ApiResponse(code = 400, message = "Error updating product price"),
                            @ApiResponse(code = 422, message = "Validation error")] )
    Response updateProduct(@ApiParam(value = "Updated Product Price Object", required = true)
                           @Valid ProductPriceEntity productPriceEntity) {

        if (productModule.updateProductPrice(productPriceEntity)) {
            return Response.ok().build()
        }
        throw new BadRequestException("Error updating product price.")
    }

}
