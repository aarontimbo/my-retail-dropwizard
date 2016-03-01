package com.myretail.dao

import com.myretail.domain.CurrencyPriceEntity
import com.myretail.domain.ProductPriceEntity
import com.myretail.transfer.CurrencyCode
import net.vz.mongodb.jackson.DBCursor
import net.vz.mongodb.jackson.JacksonDBCollection

/**
 * Implementation for performing Product Price CRUD operations with MongoDB data store
 */
class ProductPriceDAOImpl implements ProductPriceDAO {

    JacksonDBCollection<ProductPriceEntity, String> dbCollection

    ProductPriceDAOImpl(JacksonDBCollection<ProductPriceEntity, String> dbCollection) {
        this.dbCollection = dbCollection
    }

    ProductPriceEntity findByProductId(Long productId) {
        DBCursor<ProductPriceEntity> productPricesCursor = dbCollection.find()
        return productPricesCursor.find{ it.productId == productId }
    }

    void updateProductPrice(ProductPriceEntity productPriceEntity ){
//        ProductPriceEntity productPriceEntity = findByProductId(productId)
//        if (productPriceEntity) {
//            CurrencyPriceEntity existingPrice = findCurrencyPrice(productPriceEntity, currencyPriceEntity.currency_code)
//            if (existingPrice) {
//                existingPrice.value = currencyPriceEntity.value
//            }
//        }
        // not implemented yet
    }

    private CurrencyPriceEntity findCurrencyPrice(ProductPriceEntity productPriceEntity, CurrencyCode currencyCode) {
        return productPriceEntity.currencyPrices.find{ it.currency_code == currencyCode }
    }

}
