package com.myretail.dao

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.WriteResult as MongoDBWriteResult
import com.myretail.domain.ProductPriceEntity
import net.vz.mongodb.jackson.JacksonDBCollection
import net.vz.mongodb.jackson.WriteResult

/**
 * Implementation for performing Product Price CRUD operations with MongoDB data store
 */
class ProductPriceDAOImpl implements ProductPriceDAO {

    JacksonDBCollection<ProductPriceEntity, String> dbCollection

    ProductPriceDAOImpl(JacksonDBCollection<ProductPriceEntity, String> dbCollection) {
        this.dbCollection = dbCollection
    }

    ProductPriceEntity findByProductId(Long productId) {
        return dbCollection.findOne(getProductQuery(productId))
    }

    boolean updateProductPrice(ProductPriceEntity productPriceEntity ){

        WriteResult<ProductPriceEntity, MongoDBWriteResult> updateResult = 
                dbCollection.update(getProductQuery(productPriceEntity.productId), productPriceEntity)

        if (updateResult.n == 0) {
            return false
        }
        return true
    }

    private DBObject getProductQuery(Long productId) {
        return new BasicDBObject('productId', productId)
    }
}
