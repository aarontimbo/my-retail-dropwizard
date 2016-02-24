package com.myretail.transfer

/*
 * Transfer object representing product price data in the data store
 */
class ProductPrice {

    Long productId

    String currency_code = CurrencyCode.USD

    BigDecimal value

}
