package com.myretail.consumers

import com.myretail.domain.ProductDetailEntity

/**
 * Interface describing operations that may be performed
 * by the consumer of an external Product resource.
 */
interface ProductDetailConsumer {

    ProductDetailEntity getProductDetailByProductId(Long productId)

}
