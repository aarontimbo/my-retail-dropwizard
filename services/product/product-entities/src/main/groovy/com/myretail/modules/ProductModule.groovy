package com.myretail.modules

import com.myretail.consumers.ProductDetailConsumer
import com.myretail.dao.ProductPriceDAO
import com.myretail.domain.CurrencyPriceEntity
import com.myretail.domain.ProductDetailEntity
import com.myretail.domain.ProductEntity
import com.myretail.domain.ProductPriceEntity
import com.myretail.transfer.CurrencyCode

import javax.persistence.EntityNotFoundException

/**
 * Module containing business logic to handle requests for product data
 */
class ProductModule {

    private final ProductPriceDAO productPriceDAO
    private final ProductDetailConsumer productDetailConsumer

    ProductModule(ProductPriceDAO productPriceDAO, ProductDetailConsumer productDetailConsumer) {
        this.productPriceDAO = productPriceDAO
        this.productDetailConsumer = productDetailConsumer
    }

    public ProductEntity getProduct(Long productId) {
        ProductPriceEntity productPriceEntity = getProductPriceEntity(productId)
        if (!productPriceEntity) {
            throw new EntityNotFoundException("No product price found for product ID: ${productId}")
        }
        CurrencyPriceEntity currencyPriceEntity = getPriceByCurrency(productPriceEntity, CurrencyCode.USD)
        ProductDetailEntity productDetailEntity = getProductDetailEntity(productId)
        return buildProductEntity(productId, productDetailEntity, currencyPriceEntity)
    }

    //TODO handle updates to product price
    public Long updateProductPrice() {
        // TODO
    }

    private ProductPriceEntity getProductPriceEntity(Long productId) {
        return productPriceDAO.findByProductId(productId)
    }

    private ProductDetailEntity getProductDetailEntity(Long productId) {
        return productDetailConsumer.getProductDetailByProductId(productId)
    }

    private CurrencyPriceEntity getPriceByCurrency(ProductPriceEntity productPriceEntity, CurrencyCode currencyCode) {
        return productPriceEntity.currencyPrices.find { it.currency_code == currencyCode }
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
