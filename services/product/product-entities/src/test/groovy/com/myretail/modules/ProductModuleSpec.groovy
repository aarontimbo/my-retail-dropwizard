package com.myretail.modules

import spock.lang.Unroll

import static com.myretail.transfer.CurrencyCode.USD

import com.myretail.consumers.ProductDetailConsumer
import com.myretail.dao.ProductPriceDAO
import com.myretail.domain.CurrencyPriceEntity
import com.myretail.domain.ProductDetailEntity
import com.myretail.domain.ProductEntity
import com.myretail.domain.ProductPriceEntity

import spock.lang.Specification

class ProductModuleSpec extends Specification {

    private static final Long PRODUCT_ID = 123456
    private static final String PRODUCT_NAME = 'In Search of the Snark'
    private static final BigDecimal CURRENCY_AMOUNT = 12.34

    ProductDetailConsumer productDetailConsumer
    ProductPriceDAO productPriceDAO
    ProductModule productModule

    def setup() {
        productDetailConsumer = Mock()
        productPriceDAO = Mock()
        productModule = new ProductModule(productPriceDAO, productDetailConsumer)
    }

    void 'retrieve a product price object by product id'() {
        given:
        ProductDetailEntity productDetailEntity = new ProductDetailEntity(id: PRODUCT_ID, name: PRODUCT_NAME)

        CurrencyPriceEntity currencyPriceEntity = new CurrencyPriceEntity(
                currency_code: USD,
                value: CURRENCY_AMOUNT)

        ProductPriceEntity productPriceEntity = new ProductPriceEntity(
                productId: PRODUCT_ID, currencyPrices: [currencyPriceEntity])

        when:
        ProductEntity productEntity = productModule.getProduct(PRODUCT_ID)

        then:
        1 * productDetailConsumer.getProductDetailByProductId(PRODUCT_ID) >> productDetailEntity
        1 * productPriceDAO.findByProductId(PRODUCT_ID) >> productPriceEntity
        0 * _

        assert productEntity.id == PRODUCT_ID
        assert productEntity.name == productDetailEntity.name
        assert productEntity.current_value.currency_code == USD
        assert productEntity.current_value.value == CURRENCY_AMOUNT
    }

    void 'retrieving a product that does not have product detail returns null'() {
        when:
        ProductEntity productEntity = productModule.getProduct(PRODUCT_ID)

        then:
        1 * productDetailConsumer.getProductDetailByProductId(PRODUCT_ID) >> null
        0 * _

        assert !productEntity
    }

    void 'retrieving a product that does not have product price returns null'() {
        given:
        ProductDetailEntity productDetailEntity = new ProductDetailEntity(id: PRODUCT_ID, name: PRODUCT_NAME)

        when:
        ProductEntity productEntity = productModule.getProduct(PRODUCT_ID)

        then:
        1 * productDetailConsumer.getProductDetailByProductId(PRODUCT_ID) >> productDetailEntity
        1 * productPriceDAO.findByProductId(PRODUCT_ID) >> null
        0 * _

        assert !productEntity
    }

    @Unroll
    void 'update existing product price object where object updated result is #isUpdated'() {
        given:
        ProductPriceEntity productPriceEntity = new ProductPriceEntity(productId: PRODUCT_ID)

        when:
        boolean result = productModule.updateProductPrice(productPriceEntity)

        then:
        1 * productPriceDAO.updateProductPrice(productPriceEntity) >> isUpdated
        0 * _

        assert result == isUpdated
        
        where:
        isUpdated | _
        true      | _
        false     | _
    }
}
