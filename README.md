# Junit-Testing
Spring Boot CRUD islemlerine basit bir Junit test ornegi

### Tech Stack
- Java 11
- Spring Boot
- Lombok
- Spring Data
- REST-API
- Postgres
- Lombok
- Junit5


### Requirements

For building and running the application you need:
- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or newer .
- [Maven](https://maven.apache.org)
- [Lombok](https://projectlombok.org/)
- [Docker](https://www.docker.com/)
- [Junit5](https://junit.org/junit5/)


### Build & Run
Docker Run:
```
  docker-compose -f docker-compose.yml up -d
```
Docker Stop:
```
  docker-compose -f docker-compose.yml down
```

```
  mvn clean install -DskipTests
```

### Port
```
  http://localhost:8080
```