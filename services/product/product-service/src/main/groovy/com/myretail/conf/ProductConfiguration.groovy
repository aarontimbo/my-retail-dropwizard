package com.myretail.conf

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.Max
import javax.validation.constraints.Min

/**
 * Product Service configuration
 */
class ProductConfiguration extends Configuration {
    @JsonProperty
    @NotEmpty
    public String mongohost = '127.0.0.1';

    @JsonProperty
    @Min(1L)
    @Max(65535L)
    public int mongoport = 27017;

    @JsonProperty
    @NotEmpty
    public String mongodb = 'myretail';
}
