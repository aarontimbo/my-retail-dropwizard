package com.myretail.dao

import com.myretail.domain.ProductPriceEntity

/**
 * Interface describing available Product Price CRUD operations.
 */
interface ProductPriceDAO {

    ProductPriceEntity findByProductId(Long productId)

    Long updateProductPrice(ProductPriceEntity productPriceEntity)
}
