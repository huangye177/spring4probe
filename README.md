# What is spring4probe

**spring4probe** is a set of prototype tests on the capability of Springframework 4.X

## Prerequisites 

database installation:

* MongoDB 

* H2

============

## How to

[ **Must** ] Start MongoDB

* `mongod`

[ **Must** ] run a GemFire server (start a basic GemFire server with a Region -- a logical partition within GemFire named as 'YummyNoodleOrder'.) 

* `gradle run` (on port 40404)

[ Optional ] run from web container:

* `gradle build tomcatRunWar` (and visit http://localhost:8080/aggregators/orders to get an empty page with "[]" through HTTP authentication "http:http")

run from tests:

* `gradle clean test` (primary test cases: "rest.yummynoodlebar.rest.controller", "data.yummynoodlebar.persistence.integration", "com.yummynoodlebar.web.controller")

============

## Probe Content

included tutorials:

* "**rest.yummynoodlebar**" RESTful implementation and test with MockMVC/RestTemplate; deployable on Tomcat with Java-based Config; HTTP Basic security [from http://spring.io/guides/tutorials/rest/]

* "**data.yummynoodlebar**" MongoDB persistent via MongoTemplate and Spring Data repository hierarchy (e.g., CrudRepository); config class to map to a JPA database, and use Spring Data JPA Repository to store and retrieve data from H2 database; support GemFire by launching a local cache server to save/retreive objects with GemFireTemplate OQL and GemfireRepository; support of calable and event-driven architecture (propagated message across application cluster) with GemFire Continuous Queries [from http://spring.io/guides/tutorials/data/]

* "**com.yummynoodlebar**" SpringMVC based controller and mapping; [from http://spring.io/guides/tutorials/web/]
