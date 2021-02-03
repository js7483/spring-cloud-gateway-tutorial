This repository is sample project for Spring Cloud Gateway

## Prerequisites

To run this project you'll need:

* JDK 11+

## Running

Run following command to build project
```
./gradlew build
```
Run the all applications with the following command:

```
./gradlew :eureka-service:bootRun :gateway-service:bootRun :shop-service:bootRun :user-service:bootRun --parallel
```