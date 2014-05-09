# What is spring4probe

**spring4probe** is a set of prototype tests on the capability of Springframework 4.X

## Prerequisites 

Service installation:

* MongoDB 

* H2

* Redis

============

## How to

##### [ **Must** ] Start MongoDB

* `mongod` (in MongoDB directory)

##### [ **Must** ] run a Gemfire Server (for Gemfire test cases)

* `gradle runGemfire` 

start a basic GemFire server with a Region **on port 40404** -- a logical partition within GemFire named as 'YummyNoodleOrder'

##### [ **Must** ] run a Redis Server (for Redis test cases)

* `src/redis-server` (in Redis directory)

##### [ **Recommended** ] run from web container:

* `gradle build tomcatRunWar` 

(default) visit http://localhost:8080/ (for page requesting authentication, user name: "http", password: "http")

(alternative by comment `com.yummynoodlebar.config.WebAppInitializer` to ensure use of `rest.yummynoodlebar.config.WebAppInitializer`) visit http://localhost:8080/aggregators/orders to get an empty page with "[]" through HTTP authentication "http:http"

##### [ **Recommended** ] run from tests:

* `gradle clean test` 

primary test cases: `rest.yummynoodlebar.rest.controller`, `data.yummynoodlebar.persistence.integration`, `com.yummynoodlebar.web.controller`

##### [ **Optional** ] run application directly:

* `gradle [runAPP]` (for example: `gradle runScheduleTaskApp`)

Other APP options: `runScheduleTaskApp`; `runConsumeRESTApp`; `runRedisApp`; 

code package: `io.spring` 

============

## Probe Content

included tutorials:

* "**rest.yummynoodlebar**" RESTful implementation and test with MockMVC/RestTemplate; deployable on Tomcat with Java-based Config via WebApplicationInitializer; HTTP Basic security. [from http://spring.io/guides/tutorials/rest/]

* "**data.yummynoodlebar**" MongoDB persistent via MongoTemplate and Spring Data repository hierarchy (e.g., CrudRepository); config class to map to a JPA database, and use Spring Data JPA Repository to store and retrieve data from H2 database; support GemFire by launching a local cache server to save/retreive objects with GemFireTemplate OQL and GemfireRepository; support of calable and event-driven architecture (propagated message across application cluster) with GemFire Continuous Queries. [from http://spring.io/guides/tutorials/data/]

* "**com.yummynoodlebar**" SpringMVC based configuration via AbstractAnnotationConfigDispatcherServletInitializer, controller and mapping; Spring Components with multi-scopes (singleton and HttpSession); use of view-fragments supported Thymeleaf viewResolver; inject Spring MVC model into view; MockMVC to ensure attributes existence in view's model() and url forwarding/redirecting; @ModelAttribute based command object mapping between controller and view page with validation support; HTML-form, in-header-cookie JSESSIONID session, and filter-chain based Spring Security authentication with customized rules. [from http://spring.io/guides/tutorials/web/]

* "**io.Spring**" A list of Spring feature testings from Spring guide, including: Scheduling Tasks; Consuming a RESTful Web Service; Uploading Files (integrated into gradle tomcatRunWar); Messaging with Redis; [http://spring.io/guides]

* More incoming:

>Messaging with RabbitMQ; Messaging with JMS; Consuming a SOAP web service; Managing Transactions (via JdbcTemplate); Accessing Data with JPA; Accessing Data with MongoDB; Accessing JPA Data with REST; Creating a Batch Service; Application with Reactor; Using WebSocket; Uploading pictures with Reactor;




