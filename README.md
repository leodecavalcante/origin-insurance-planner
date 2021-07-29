# Origin Insurance Planner
Project designed with Hexagonal Architecture made to plan a insurance package personalized based on the user personal given information.

## What was used?
1. [Kotlin](https://kotlinlan.org/)
2. [Gradle](https://gradle.org/)
3. [Spring boot](https://spring.io/projects/spring-boot)
4. [Docker](https://www.docker.com/)

## Getting Started
### Build
    ./gradlew build

### Test
    ./gradlew test

### Run
    ./gradlew bootRun

### Build deployable docker image

    ./gradlew clean build
    docker build -t <docker-repository:tag> .