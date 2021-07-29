# Origin Insurance Planner
Project designed with Hexagonal Architecture made to plan an insurance personalized package based on the user personal given information.

## What was used?
1. [Kotlin](https://kotlinlan.org/)
2. [Gradle](https://gradle.org/)
3. [Spring boot](https://spring.io/projects/spring-boot)
4. [Docker](https://www.docker.com/)

## Getting Started
You will need to have your JAVA_HOME environment variable set with java 11 or above. 

### Build
    ./gradlew build

### Test
    ./gradlew test

### Run
    ./gradlew bootRun

### Build deployable docker image

    ./gradlew clean build
    docker build -t <docker-repository:tag> .

## Available endpoints
Endpoint | Description
--- | ---
GET /api/v1/insurance/plan | Plan insurance personalized package
GET /actuator/health | Checks application health

### Plan insurance personalized package
    curl -X GET http://localhost:8080/api/v1/insurance/plan

#### Request
```json
{
  "age": 35,
  "dependents": 2,
  "house": {"ownership_status": "owned"},
  "income": 0,
  "marital_status": "married",
  "risk_questions": [0, 1, 0],
  "vehicle": {"year": 2018}
}
```

#### Response

Status 200 OK

```json
{
    "auto": "regular",
    "disability": "ineligible",
    "home": "economic",
    "life": "regular"
}
```

### Plan insurance personalized package
    curl -X GET http://localhost:8080/actuator/health

Status 200 OK

#### Response
```json
{
  "status": "UP"
}
```