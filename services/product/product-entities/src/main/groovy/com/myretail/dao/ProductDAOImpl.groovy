package com.myretail.dao

import com.myretail.domain.ProductPriceEntity
import net.vz.mongodb.jackson.DBCursor
import net.vz.mongodb.jackson.JacksonDBCollection

/**
 * Implementation for performing Product Price CRUD operations with MongoDB data store
 */
class ProductPriceDAOImpl implements ProductPriceDAO{

    JacksonDBCollection<ProductPriceEntity, String> dbCollection

    ProductPriceDAOImpl(JacksonDBCollection<ProductPriceEntity, String> dbCollection) {
        this.dbCollection = dbCollection
    }

    ProductPriceEntity findByProductId(Long productId) {
        DBCursor<ProductPriceEntity> productPricesCursor = dbCollection.find()
        return productPricesCursor.find{ it.productId == productId }
    }

    Long updateProductPrice(ProductPriceEntity productPriceEntity){
        // not implemented yet
    }

}
