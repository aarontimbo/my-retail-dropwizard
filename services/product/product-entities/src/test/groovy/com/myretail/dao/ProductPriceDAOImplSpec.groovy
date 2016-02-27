package com.myretail.dao

import com.github.fakemongo.Fongo
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.myretail.domain.ProductPriceEntity

import spock.lang.Specification

class ProductPriceDAOImplSpec extends Specification {

    private static final Long PRODUCT_ID = 123456
    private static final String COLLECTION_NAME = 'products'

    DBCollection collection
    ProductPriceDAOImpl productPriceDAO

    def setup() {
        Fongo mongo = new Fongo('fakeServer')
        DB db = mongo.getDB('test')

        productPriceDAO = new ProductPriceDAOImpl(db)
        collection = db.getCollection(COLLECTION_NAME)
        collection.insert(new BasicDBObject("productId", PRODUCT_ID))
    }

    void 'retrieve product price object by product id'() {
        given:

        when:
        ProductPriceEntity productPriceEntity = productPriceDAO.findByProductId(PRODUCT_ID)

        then:
        assert productPriceEntity
        assert productPriceEntity.productId == PRODUCT_ID
    }
}
