FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-bancos

COPY ./pom.xml /app

COPY ./msvc-bancos/.mvn ./.mvn

COPY ./msvc-bancos/mvnw .

COPY ./msvc-bancos/pom.xml .

RUN ./mvnw dependency:go-offline

#Las instrucciones de arriba estaran en cache

COPY ./msvc-bancos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc-bancos/target/msvc-bancos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

CMD ["java", "-jar", "msvc-bancos-0.0.1-SNAPSHOT.jar"]