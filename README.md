# Flight Search API
Flight Search API contains REST endpoint in order to search flight details and render the requested details as JSON response to end user. The response from REST APIs can be further integrated with front end view for better presentation.

### System Design
Flight Search API is microservice based layered architectured RESTful Web Service. This service can be deployed independently on premise / cloud and can also be containerized to execute as docker containers. There are 4 layers from top to bottom:
- API Layer
  - Top layer, which is main interface available for intgeration and interaction with front-end or end user to consume APIs.
  - Contains secured API end points implementation.
  - [Springboot-starter-security](https://spring.io/guides/gs/securing-web/) Module along with JWT is used to implement authentication for APIs. 
  - [Springboot-starter-web](https://spring.io/guides/gs/rest-service/) module used as a framework to implement Restful api end points. 
- Service Layer
  - This layer sits in between API layer and Data access layer with some utility functionality.
  - Mainly responsible for interacting with Data Access Layer and transferring the flight data as required by top and below layers.
  - It's just another module added to decouple business logic of flight data transfer and mapping from/to API layer.
  - Further, service layer can be enhanced to support advanved features like Caching, Interacting with external Authorization Service etc.
- Data Access Layer
  - Responsible to provide Object Relationship Mapping (ORM) between higher level flight Java objects and persistence layer tables.
  - [Springboot-starter-data-JPA](https://spring.io/guides/gs/accessing-data-jpa/) module is used to implement mappings between objects and tables.
  - This layer contains flight entity classes and JPA repositories which implement lower level functionality of storing/retrieving recipes data.  
- Persistence Layer
  - Bottom most layer, responsible for physically storing the recipes data onto database table.
  - Just one physical table - `flight` is used to store the flight data for the service.
  - For development and testing purposes, the Embedded H2 Database provided by Spring Boot framework is also utilized. 

### Supported Features
| Feature                     | Software Module Used                                                                                         |
|-----------------------------|--------------------------------------------------------------------------------------------------------------|
| RESTful API                 | [Springboot](https://spring.io/projects/spring-boot)                                                         |
| API Authentication          | [Spring Security](https://spring.io/projects/spring-security) with JWT Token                                 |
| Object Relationship Mapping | [Spring Data JPA](https://spring.io/projects/spring-data-jpa)                                                |
| Exception Handling          | [Controller Advice and ExceptionHandler](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc) |
| Logging                     | [SLF4J](http://www.slf4j.org/manual.html) Logger                                                             |
| Unit Tests                  | Junit 5 with [AssertJ](https://assertj.github.io/doc/)                                                       |

### Prerequisites
* [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11)
* [Apache Maven](https://maven.apache.org/)
* [H2DBSQL](https://www.mysql.com/)
* [Git](https://git-scm.com/)
* [QueryDSL](https://querydsl.com//)
## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `FlightApiApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

For dev environment:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

For production environment:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Running the application in Docker

Run following command

```shell
mvn spring-boot:build-image
```

**For this to work, we need to have Docker installed and running.**

Then to start the container, we can simply run:

```shell
docker run -it -p8080:8080 flight-api:1.0-SNAPSHOT
```

## API details and testing

you can open swagger documentation page:
http://localhost:8080/swagger-ui.html

### Future Enhancements
- Integrate REST API with Authorisation server for authentication and authorisation.
- Design and Implementation of Multi Factor Authentication feature.
- Design and build simple,beautiful front end view.
- Integration between Backend API with Front End view.

