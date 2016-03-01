package com.myretail.domain

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.validation.constraints.NotNull

/**
 * Representation of persisted product price
 */
class ProductPriceEntity {

    @JsonIgnore
    String _id

    @NotNull
    Long productId

    List<CurrencyPriceEntity> currencyPrices

}
