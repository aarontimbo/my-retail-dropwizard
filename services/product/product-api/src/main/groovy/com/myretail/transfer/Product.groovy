package com.myretail.transfer

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import javax.validation.constraints.NotNull

/*
 * Transfer object representing product data
 */
@JsonPropertyOrder(alphabetic=true)
class Product {

    @NotNull
    Long id

    @NotNull
    String name

    ProductPrice current_value
}
