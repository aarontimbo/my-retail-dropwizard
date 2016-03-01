package com.myretail.consumers

import com.google.common.base.Optional
import com.myretail.domain.ProductDetailEntity
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.apache.http.client.HttpClient
import org.apache.http.client.HttpResponseException
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.HttpClients

/**
 * Implementation of a consumer of an external Product resource.
 */
@Slf4j
class ProductDetailConsumerImpl implements ProductDetailConsumer {

    private final HttpClient httpClient = HttpClients.createDefault()
    private final JsonSlurper jsonSlurper = new JsonSlurper()

    private static final String BASE_URL = 'https://api.target.com/products/v3/'
    private static final String URL_SUFFIX = '?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz'

    /**
     * Makes a request to an external resource for product details
     * and builds an {@link ProductDetailEntity} to return.
     *
     * @param productId     Product ID to use for the request
     * @return
     */
    public ProductDetailEntity getProductDetailByProductId(Long productId) {
        String url = getProductUrl(productId)
        Optional<String> response = getProductDetail(url)
        log.debug "Product Details for product id: ${productId} - ${response}"
        return buildProductDetail(productId, response)
    }

    private Optional<String> getProductDetail(String url) {
        return getResponseAsString(buildHttpGet(url));
    }

    private HttpGet buildHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        return httpGet;
    }

    private Optional<String> getResponseAsString(HttpGet httpGet) {
        Optional<String> result = Optional.absent()
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler()
            result = Optional.of(httpClient.execute(httpGet, responseHandler))
        } catch (HttpResponseException httpResponseException) {
            log.error("getResponseAsString(): caught 'HttpResponseException' while processing request <" +
                    httpGet.toString() + "> :=> <" +
                    httpResponseException.getMessage() + ">")
        } catch (IOException ioe) {
            log.error("getResponseAsString(): caught 'IOException' while processing request <" +
                    httpGet.toString() + "> :=> <" + ioe.getMessage() + ">")
        } finally {
            httpGet.releaseConnection()
        }
        return result
    }

    private String getProductUrl(Long productId) {
        return BASE_URL + productId + URL_SUFFIX
    }

    /**
     * Build a {@link ProductDetailEntity} from the JSON response. If the JSON does
     * not contain a description (i.e. name) of the a product, returns null.
     *
     * @param productId     Product ID to assign to the {@link ProductDetailEntity}
     * @param optional      JSON to be parsed
     * @return
     */
    private ProductDetailEntity buildProductDetail(Long productId, Optional<String> optional) {
        def obj = jsonSlurper.parseText(optional.get())

        String productName = getProductName(obj)
        if (productName) {
            return new ProductDetailEntity(id: productId, name: productName)
        }
        return null
    }

    private String getProductName(def obj) {
        return obj?.product_composite_response?.items[0]?.general_description
    }
}
