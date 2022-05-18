# star-wars-api-test
Star wars films API

## Language and FrameWorks
- Java 11
- SpringBoot 2.5
- Maven 4.0.0
- Project Lombok
- OpenFeign
- Aspect4J
- Junit 5
- Mockito
- MockMvc

## How to run the application
- You can run manually in case you have maven and JDK installed on your machine
```
# to to project root folder and run the following commands:

# build project
mvn clean package

# run project
java -jar target/star-wars-api.jar
``` 

- I also provided a Dockerfile in case you don`t have JDK and Maven installed
```
# to to project root folder and run the following commands:

# Build container
sudo docker build -t star-wars-api-java-container:1.0 .

# Run the container
sudo docker run -d -p 8080:8080 -t star-wars-api-java-container:1.0
```

## I added swagger to help test the API and documentation.
- http://localhost:8080/swagger-ui.html

## Doubt about
- Criar um endpoint onde é possível detalhar um filme especifico
  - não sei se tive o entendimento correto desse task.
  - Eu entendi que eu devo prover um servico que permite alterar todos os campos do filme.

## Extra Features
- I've added unit tests using JUnit5, Mockito and MockMvc
- I've added an Aspect to log all processes
- I've added an Exception handler to deal with http request errors

## Notes
- I wasn't able to test FeignClient and Converter due to no time available.