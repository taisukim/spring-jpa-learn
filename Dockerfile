FROM gradle:7.4-jdk11 as builder
WORKDIR /build

COPY build.gradle settings.gradle /build/

RUN gradle build -x test --parallel --contibue > /dev/null 2>&1 || true

COPY docker /build
RUN gradle build -x test --parallel

# APP
FROM openjdk:11
WORKDIR /app
COPY --from=builder /build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

USER nobody
ENTRYPOINT ["java", "-jar", "app.jar"]
