# my-retail-dropwizard

Web application built with the [Dropwizard](http://www.dropwizard.io/0.9.2/docs/index.html) framework and written in [Groovy](http://www.groovy-lang.org/) for retrieving and updating product information.

## Prerequisites

* Java 1.7
* Gradle 2.x
* A locally running MongoDB instance

## Setup

* Clone the project
* Open a terminal and navigation to the `my-retail-dropwizard` project directory
* Bootstrap product data in the locally running instance of MongoDB by switching into the `mongodb-bootstrap` sub-project directory and running:

    gradle loadJson -PdbName=myretail -PcollectionName=products -PjsonFile=data/myretail-products.json

## Running the application

* ###From the Command line:

** Navigate to the `product-service` sub-project (services/product/product-service) from the `my-retail-dropwizard` project
** Start the service by executing:

    gradle run

* ###With IntelliJ IDEA

** Import project
** Select `my-retail`dropwizard` project directory
** Import project from external_model -> Gradle
** Locate the `ProductApplication.groovy` class
** Start the service by running the `main` method in the `ProductApplication.groovy` class. NOTE: The Program Arguments in the IntelliJ run configuration will need to be `server src/main/resources/local_config.yml` and the working directory should be `product-service`.

## Accessing the Endpoints

* Point your browser to [http://localhost:8080/products/13860428](http://localhost:8080/products/13860428)
* To view the API in Swagger UI, go [here](http://localhost:8080/swagger)
* For application metrics, [http://localhost:8081](http://localhost:8081)