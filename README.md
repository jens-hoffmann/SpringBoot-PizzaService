# Pizza Service - Spring Boot Web MVC Demo with Camunda BPMN workflow service

This is a small prototype demo for Spring Boot Web MVC connected to Camunda BPMN workflow engine

## Prerequisite

* Java 11+
* Maven
* Docker

## Build Project

#### Compile 

<code>mvn clean compile</code>

#### Run Services in testing mode
- 
- Start ActiveMQ message broker

<code>docker run -e AMQ_USER=admin -e AMQ_PASSWORD=secretsofadmin -p8161:8161 -p61616:61616 -p5672:5672 --name artemis quay.io/artemiscloud/activemq-artemis-broker</code>

- Start Spring Boot Web MVC app

<code>mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"</code>

- Start Camunda Workflow service

<code>mvn spring-boot:run</code>

#### Build docker image

This command may require root or administrator privileges if you are not running docker rootless ! 


<code>cd springbootwebapp</code>
<code>mvn spring-boot:build-image -Dspring-boot.run.arguments="--spring.profiles.active=mysql" -DskipTests</code>

<code>cd workflowservice</code>
<code>mvn spring-boot:build-image -DskipTests</code>

#### Run docker compose

- Start docker compose file:

<code>docker-compose up -d</code>

- Check log messages of docker - startup may take a while:

<code>docker-compose logs -f</code>

- Stop and remove container after usage: 

<code>docker-compose down</code>

#### Check out running services

Take your browser and checkout running services.

[SpringBoot App](http://localhost:8080)

[Camunda Workflow Engine](http://localhost:8081)  Login with user: demo and password: demo for demonstration purposes

[Artemis](http://localhost:8161) replace localhost with your local ip address, otherwise navigation bar in Artemis UI will stay empty

[PHPMyAdmin](http://localhost:8090)  for demonstration purposes with database administrator privileges 

[Prometheus](http://localhost:9090)

[Granfana](http://localhost:3000) Login with user: admin and password: admin  for demonstration purposes 


