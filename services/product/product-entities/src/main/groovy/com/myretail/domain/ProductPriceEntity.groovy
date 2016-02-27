package com.myretail.domain

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * Representation of persisted product price
 */
class ProductPriceEntity {

    @JsonIgnore
    String _id

    Long productId

    List<CurrencyPriceEntity> currencyPrices

}
