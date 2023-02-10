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

<code>mvn spring-boot:build-image -DskipTests</code>

4. Run docker compose

Start docker compose file:

<code>docker-compose up -d</code>

Check log messages of docker

<code>docker-compose logs -f</code>

5. Check out running services

Take your browser and checkout running services.

[SpringBoot App](http://localhost:8080)

[PHPMyAdmin](http://localhost:8090)  for demonstration purposes with database administrator privileges 

[Prometheus](http://localhost:9090)

[Granfana](http://localhost:3000) Login with user: admin and password: admin  for demonstration purposes 


