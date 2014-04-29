spring4probe
============

## How to

run from web container:

* `gradle build tomcatRunWar` (and visit http://localhost:8080/aggregators/orders to get an empty page with "[]" through HTTP authentication "http:http")

run from tests:

* `gradle clean test` (primary test cases: "*.yummynoodlebar.rest.controller", "*.yummynoodlebar.persistence.integration")

============
## Probe Content

included tutorials:

* RESTful implementation and test with MockMVC/RestTemplate; deployable on Tomcat with Java-based Config; HTTP Basic security [from http://spring.io/guides/tutorials/rest/]

* MongoDB persistent via MongoTemplate and Spring Data repository hierarchy (e.g., CrudRepository); [from http://spring.io/guides/tutorials/data/]

