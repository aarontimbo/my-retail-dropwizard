package com.myretail.dao

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.WriteResult as MongoDBWriteResult
import com.myretail.domain.ProductPriceEntity
import groovy.util.logging.Slf4j
import net.vz.mongodb.jackson.DBCursor
import net.vz.mongodb.jackson.JacksonDBCollection
import net.vz.mongodb.jackson.WriteResult

/**
 * Implementation for performing Product Price CRUD operations with MongoDB data store
 */
Slf4j
class ProductPriceDAOImpl implements ProductPriceDAO {

    JacksonDBCollection<ProductPriceEntity, String> dbCollection

    ProductPriceDAOImpl(JacksonDBCollection<ProductPriceEntity, String> dbCollection) {
        this.dbCollection = dbCollection
    }

    ProductPriceEntity findByProductId(Long productId) {
        DBCursor<ProductPriceEntity> productPricesCursor = dbCollection.find()
        return productPricesCursor.find{ it.productId == productId }
    }

    boolean updateProductPrice(ProductPriceEntity productPriceEntity ){
        DBObject obj = new BasicDBObject('productId', productPriceEntity.productId)
        WriteResult<ProductPriceEntity, MongoDBWriteResult> updateResult = dbCollection.update(obj, productPriceEntity)
        if (updateResult.writeResult.error) {
            log.error("Unable to update product with ID: ${productPriceEntity.productId}. " +
                      "Error: ${updateResult.writeResult.error}")
            return false
        }
        return true
    }

}
