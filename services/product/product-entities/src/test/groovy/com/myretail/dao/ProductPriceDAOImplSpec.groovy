package com.myretail.dao


import static com.myretail.transfer.CurrencyCode.USD

import com.github.fakemongo.Fongo
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.myretail.domain.CurrencyPriceEntity
import com.myretail.domain.ProductPriceEntity
import net.vz.mongodb.jackson.JacksonDBCollection
import spock.lang.Specification

class ProductPriceDAOImplSpec extends Specification {

    private static final Long PRODUCT_ID = 123456
    private static final Double PRODUCT_PRICE = 12.34
    private static final String COLLECTION_NAME = 'products'

    DBCollection collection
    ProductPriceDAOImpl productPriceDAO

    def setup() {
        Fongo mongo = new Fongo('fakeServer')
        DB db = mongo.getDB('test')
        collection = db.getCollection(COLLECTION_NAME)
        initializeCollection(collection)

        JacksonDBCollection<ProductPriceEntity, String> dbCollection =
            JacksonDBCollection.wrap(db.getCollection(COLLECTION_NAME), ProductPriceEntity, String)

        productPriceDAO = new ProductPriceDAOImpl(dbCollection)
    }

    void 'retrieve product price object by product id'() {
        when:
        ProductPriceEntity productPriceEntity = productPriceDAO.findByProductId(PRODUCT_ID)

        then:
        assert productPriceEntity
        assert productPriceEntity.productId == PRODUCT_ID
        assert productPriceEntity.currencyPrices[0].currency_code == USD
        assert productPriceEntity.currencyPrices[0].value == PRODUCT_PRICE
    }

    void 'update product price'() {
        given:
        Double updatedPrice = 999.99
        CurrencyPriceEntity updatedCurrencyPrice = new CurrencyPriceEntity(
                currency_code: USD,
                value: updatedPrice
        )
        ProductPriceEntity updatedProductPrice = new ProductPriceEntity(
                productId: PRODUCT_ID,
                currencyPrices: [updatedCurrencyPrice]
        )

        when:
        boolean result = productPriceDAO.updateProductPrice(updatedProductPrice)
        ProductPriceEntity productPriceEntity = productPriceDAO.findByProductId(PRODUCT_ID)

        then:
        assert result
        assert productPriceEntity.currencyPrices[0].value == updatedPrice
    }

    private void initializeCollection(DBCollection collection) {
        Map currencyPrice =  [
                currency_code: USD.toString(),
                value: PRODUCT_PRICE

        ]
        Map product = [
            productId: PRODUCT_ID,
            currencyPrices: [ currencyPrice ]
        ]
        collection.insert(new BasicDBObject(product))
    }
}
