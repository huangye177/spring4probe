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

##### [ **Must** ] run a Gemfire Server (for running all test cases, which contains GemFire related test cases)

* `gradle runGemfire` 

start a basic GemFire server with a Region **on port 40404** -- a logical partition within GemFire named as 'YummyNoodleOrder'

##### [ **Optional** ] run a Redis Server (for Redis test cases)

* `bin/redis-server` (in Redis directory, e.g., `/usr/local/Cellar/redis/2.8.9` on Mac via brew)

##### [ **Optional** ] run a RabbitMQ Server (for RabbitMQ test cases)

* `sbin/rabbitmq-server` (in RabbitMQ directory, e.g., `/usr/local/sbin/` on Mac via brew)

>associated commands: `rabbitmqctl stop`; you might also want to check the allow net interface in `/usr/local/etc/rabbitmq/rabbitmq-env.conf`

##### [ **Optional** ] run a ActiveMQ Server (for JMS/ActiveMQ test cases)

* `bin/activemq start` (in ActiveMQ directory, e.g., `/usr/local/Cellar/activemq/5.9.1/` on Mac via brew)

##### [ **Recommended** ] run from web container:

* `gradle build tomcatRunWar` 

(default) visit http://localhost:8080/ (for page requesting authentication, user name: "http", password: "http")

(alternative by comment `com.yummynoodlebar.config.WebAppInitializer` to ensure use of `rest.yummynoodlebar.config.WebAppInitializer`) visit http://localhost:8080/aggregators/orders to get an empty page with "[]" through HTTP authentication "http:http"

##### [ **Recommended** ] run from tests:

* `gradle clean test` 

primary test cases: `rest.yummynoodlebar.rest.controller`, `data.yummynoodlebar.persistence.integration`, `com.yummynoodlebar.web.controller`

##### [ **Optional** ] run application directly:

* `gradle [runAPP]` (for example: `gradle runScheduleTaskApp`)

Other APP options: `runScheduleTaskApp`; `runConsumeRESTApp`; `runRedisApp`; `runRabbitMQApp`; `runJMSApp`;

code package: `io.spring` 

============

## Probe Content

included tutorials:

* "**rest.yummynoodlebar**" RESTful implementation and test with MockMVC/RestTemplate; deployable on Tomcat with Java-based Config via WebApplicationInitializer; HTTP Basic security. [from http://spring.io/guides/tutorials/rest/]

* "**data.yummynoodlebar**" MongoDB persistent via MongoTemplate and Spring Data repository hierarchy (e.g., CrudRepository); config class to map to a JPA database, and use Spring Data JPA Repository to store and retrieve data from H2 database; support GemFire by launching a local cache server to save/retreive objects with GemFireTemplate OQL and GemfireRepository; support of calable and event-driven architecture (propagated message across application cluster) with GemFire Continuous Queries. [from http://spring.io/guides/tutorials/data/]

* "**com.yummynoodlebar**" SpringMVC based configuration via AbstractAnnotationConfigDispatcherServletInitializer, controller and mapping; Spring Components with multi-scopes (singleton and HttpSession); use of view-fragments supported Thymeleaf viewResolver; inject Spring MVC model into view; MockMVC to ensure attributes existence in view's model() and url forwarding/redirecting; @ModelAttribute based command object mapping between controller and view page with validation support; HTML-form, in-header-cookie JSESSIONID session, and filter-chain based Spring Security authentication with customized rules. [from http://spring.io/guides/tutorials/web/]

* "**io.Spring**" A list of Spring feature testings from Spring guide, including: Scheduling Tasks; Consuming a RESTful Web Service; Uploading Files (integrated into gradle tomcatRunWar); Messaging with Redis; Messaging with RabbitMQ; Messaging with JMS; [http://spring.io/guides]

* More incoming:

>Consuming a SOAP web service; Managing Transactions (via JdbcTemplate); Accessing Data with JPA; Accessing Data with MongoDB; Accessing JPA Data with REST; Creating a Batch Service; Application with Reactor; Using WebSocket; Uploading pictures with Reactor;




