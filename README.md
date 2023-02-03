# Pizza Service - Spring Boot Web MVC Demo

This is a small prototype demo for Spring Boot Web MVC 

### Prerequisite

* Java 11+
* Maven
* Docker

### Build Project

1. Compile 

<code>mvn clean compile</code>

2. Run Spring Boot in dev mode with H2 memory database

<code>mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"</code>

3. Build docker image

This command may require root or administrator privileges if you are not running docker rootless ! 

<code>mvn spring-boot:build-image</code>
