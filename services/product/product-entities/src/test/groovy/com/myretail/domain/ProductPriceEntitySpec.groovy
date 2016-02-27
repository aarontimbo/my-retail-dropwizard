package com.myretail.domain

import static io.dropwizard.testing.FixtureHelpers.*
import io.dropwizard.jackson.Jackson
import com.fasterxml.jackson.databind.ObjectMapper

import spock.lang.Specification

class ProductPriceEntitySpec extends Specification {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper()

    void 'serializes domain object to json'() {
        setup:
        CurrencyPriceEntity currencyPriceEntity = new CurrencyPriceEntity(currency_code: 'USD', value: 12.34)
        ProductPriceEntity entity = new ProductPriceEntity(productId: 123456, currencyPrices: [currencyPriceEntity])

        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture('fixtures/productPrice.json'), ProductPriceEntity))

        expect:
        assert expected == MAPPER.writeValueAsString(entity)
    }
}
