package com.myretail.domain

import javax.validation.constraints.NotNull

/**
 * Representation of a Product
 */
class ProductEntity {

    @NotNull
    Long id

    @NotNull
    String name

    CurrencyPriceEntity current_value

}
