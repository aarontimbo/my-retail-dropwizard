package com.myretail.resources

import com.myretail.domain.ProductEntity
import com.myretail.domain.ProductPriceEntity
import com.myretail.modules.ProductModule
import spock.lang.Specification

import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response

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

    void 'update an existing product'() {
        given:
        ProductPriceEntity productPriceEntity = new ProductPriceEntity(productId: PRODUCT_ID)
        
        when:
        Response response = productResource.updateProduct(productPriceEntity)

        then:
        1 * productModule.updateProductPrice(productPriceEntity) >> true
        0 * _

        assert response
        assert response.status == 200
    }

    void 'bad update request throws exception'() {
        when:
        productResource.updateProduct(new ProductPriceEntity())

        then:
        1 * productModule.updateProductPrice(_) >> false
        0 * _

        thrown(BadRequestException)
    }
}
