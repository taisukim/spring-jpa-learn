FROM adoptopenjdk/openjdk11

ARG JAR_FILE=build/libs/*SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.yml /app/config/application.yml

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]


#FROM gradle:7.4-jdk11 as builder
#WORKDIR /build
#
#COPY build.gradle settings.gradle /build/
#
#RUN gradle build -x test --parallel --contibue > /dev/null 2>&1 || true
#
#COPY src/main/resources/application.yml /app/config/application.yml
#
#COPY docker /build
#RUN gradle build -x test --parallel
#
## APP
#FROM openjdk:11
#WORKDIR /app
#COPY --from=builder /build/libs/*-SNAPSHOT.jar ./app.jar
#
#EXPOSE 8080
#
#USER nobody
#ENTRYPOINT ["java", "-jar", "app.jar"]
