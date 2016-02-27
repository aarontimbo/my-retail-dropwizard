package com.myretail.dao

import com.mongodb.DB
import com.myretail.domain.ProductPriceEntity
import net.vz.mongodb.jackson.DBCursor
import net.vz.mongodb.jackson.JacksonDBCollection

/**
 * Implementation for performing Product Price CRUD operations with MongoDB data store
 */
class ProductPriceDAOImpl implements ProductPriceDAO{

    private static final String COLLECTION_NAME = 'products'

    DB db

    ProductPriceDAOImpl(DB db) {
        this.db = db
    }

    ProductPriceEntity findByProductId(Long productId) {
        JacksonDBCollection<ProductPriceEntity, String> productPrices = getCollection()
        DBCursor<ProductPriceEntity> productPricesCursor = productPrices.find()
        return productPricesCursor.find{ it.productId == productId }
    }

    Long updateProductPrice(ProductPriceEntity productPriceEntity){
        // not implemented yet
    }

    private JacksonDBCollection<ProductPriceEntity, String> getCollection() {
        return JacksonDBCollection.wrap(db.getCollection(COLLECTION_NAME), ProductPriceEntity, String)
    }

}
