# Job API - Spring Boot Application

RESTful API built with Spring Boot that manages job data. It has been built as a proof-of-concept to
provide an example of using architecture testing via ArchUnit.

## Prerequisites

1. Git installed (`git --version`).
2. Java JDK 21 installed and environment variables configured (`java -version`).
3. Apache Maven installed and environment variables configured (`mvn -v`).

## API Overview

### Endpoints

|     Description      | CRUD Operation |  Endpoint  | Parameter |     Payload      |
|:--------------------:|:--------------:|:----------:|:---------:|:----------------:|
| Returning Job Via Id |      GET       | /jobs/{id} |    Id     |       N/A        |
| Update Existing Job  |      PUT       | /jobs/{id} |    Id     | JobRequest.class |
| Remove Existing Job  |     DELETE     | /jobs/{id} |    Id     |       N/A        |
|   Return All Jobs    |      GET       |   /jobs    |    N/A    |       N/A        |
|    Create New Job    |      POST      |   /jobs    |    N/A    | JobRequest.class |

### Payload

|   Object Name    |     Fields     |            Description             |  Type  |         Validation          |
|:----------------:|:--------------:|:----------------------------------:|:------:|:---------------------------:|
| JobRequest.class |    jobName     |        Name of the Job Role        | String |     1 - 100 Characters      |
|                  | jobDescription |    Description of the Job Role     | String |     1 - 1000 Characters     |
|                  |   capability   | Capability the Job Role Belongs To |  Enum  | Valid Capability Enum Value |
|                  |      band      |      The Band of the Job Role      |  Enum  |    Valid Band Enum Value    |

## Running The Application

#### Build The project

```bash
mvn clean install
```

#### Run The Spring Boot App

```bash
mvn spring-boot:run
```

#### Run Via Docker Image

```bash
docker compose up -d --build
```

Swagger UI is available at: http://localhost:8988/swagger-ui.html

## Running Tests

#### Unit Tests

```bash
mvn clean test
```

#### Unit & Integration Tests

```bash
mvn clean integration-test
```

#### Run Tests W/ Code Coverage

```bash
mvn clean verify
```

### Maven Profiles

| Profile              | Description                     |
|----------------------|---------------------------------|
| `architecture-tests` | Runs ArchUnit Tests             |
| `mutation-tests`     | Enables PiTest Mutation Testing |
| `dependency-check`   | Runs OWASP Dependency Check     |

#### Run Architecture Tests Only

```bash
mvn clean verify -Parchitecture-tests
```

#### Check Code Coverage & Run Mutation Tests

```bash
mvn clean verify -Pmutation-tests
````

#### Running Mutation Tests Only (No Configuration) _The Project MUST Be Built First_

```bash
mvn org.pitest:pitest-maven:mutationCoverage
````

## Other Commands

#### Run OWASP Dependency Check

_It is recommended to add an NVD API Key to your maven settings to reduce the runtime for this profile_

```bash
mvn verify -Pdependency-check
```

#### Check For Dependency Upgrades

```bash
mvn versions:display-property-updates
```

## Report Locations

Once you have executed the tests via the CLI, Jacoco, PiTest and SpotBugs will generate a report.
JaCoCo is used for code coverage, PiTest measures test quality via mutation testing, while SpotBugs identifies bug
patterns.

They are located in the following:

JaCoCo Coverage Report: `target/site/jacoco/index.html`

PiTest Mutation Report: `target/pit-reports/index.html`

SpotBugs Report: `target/spotbugs/spotbugs.html`

If you execute the dependency-check profile this will also generate a report. It can be located here:

Dependency Check Report: `target/dependency-check/dependency-check-report.html`
