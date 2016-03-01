package com.myretail.resources

import com.myretail.domain.ProductEntity
import com.myretail.modules.ProductModule
import spock.lang.Specification

import javax.ws.rs.NotFoundException

class ProductResourceSpec extends Specification {

    private static final Long PRODUCT_ID = 123456

    ProductModule productModule
    ProductResource productResource

    def setup() {
        productModule = Mock()

        productResource = new ProductResource(productModule)
    }

    void 'find product by product id'() {
        when:
        ProductEntity productEntity = productResource.findProduct(PRODUCT_ID)

        then:
        1 * productModule.getProduct(PRODUCT_ID) >> { new ProductEntity(id: PRODUCT_ID) }
        0 * _

        assert productEntity
        assert productEntity.id == PRODUCT_ID
    }

    void 'find product by throws not foudn exception if product does not exist'() {
        when:
        productResource.findProduct(PRODUCT_ID)

        then:
        1 * productModule.getProduct(PRODUCT_ID) >> null
        0 * _

        thrown(NotFoundException)
    }

}
