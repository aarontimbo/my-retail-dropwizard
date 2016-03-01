package com.myretail.modules

import com.myretail.consumers.ProductDetailConsumer
import com.myretail.dao.ProductPriceDAO
import com.myretail.domain.CurrencyPriceEntity
import com.myretail.domain.ProductDetailEntity
import com.myretail.domain.ProductEntity
import com.myretail.domain.ProductPriceEntity
import com.myretail.transfer.CurrencyCode
import groovy.util.logging.Slf4j

/**
 * Module containing business logic to handle requests for product data
 */
@Slf4j
class ProductModule {

    private final ProductPriceDAO productPriceDAO
    private final ProductDetailConsumer productDetailConsumer

    ProductModule(ProductPriceDAO productPriceDAO, ProductDetailConsumer productDetailConsumer) {
        this.productPriceDAO = productPriceDAO
        this.productDetailConsumer = productDetailConsumer
    }

    public ProductEntity getProduct(Long productId) {
        ProductDetailEntity productDetailEntity = getProductDetailEntity(productId)
        if (!productDetailEntity) {
            log.warn "No product details found for product ID: ${productId}"
            return null
        }

        ProductPriceEntity productPriceEntity = getProductPriceEntity(productId)
        if (!productPriceEntity) {
            log.warn "No product price found for product ID: ${productId}"
            return null
        }

        CurrencyPriceEntity currencyPriceEntity = getPriceByCurrency(productPriceEntity, CurrencyCode.USD)
        if (!currencyPriceEntity) {
            log.warn "No ${CurrencyCode.USD} currency value found for product ID: ${productId}"
        }
        return buildProductEntity(productId, productDetailEntity, currencyPriceEntity)
    }

    public boolean updateProductPrice(ProductPriceEntity productPriceEntity) {
        return productPriceDAO.updateProductPrice(productPriceEntity)
    }

    private ProductPriceEntity getProductPriceEntity(Long productId) {
        return productPriceDAO.findByProductId(productId)
    }

    private ProductDetailEntity getProductDetailEntity(Long productId) {
        return productDetailConsumer.getProductDetailByProductId(productId)
    }

    private CurrencyPriceEntity getPriceByCurrency(ProductPriceEntity productPriceEntity, CurrencyCode currencyCode) {
        return productPriceEntity?.currencyPrices.find { it.currency_code == currencyCode }
    }

    private ProductEntity buildProductEntity(Long productId,
                                             ProductDetailEntity productDetailEntity,
                                             CurrencyPriceEntity currencyPriceEntity) {
        return new ProductEntity(
                id: productId,
                name: productDetailEntity ? productDetailEntity.name: 'foo',
                current_value: currencyPriceEntity)
    }
}
