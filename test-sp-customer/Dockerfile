FROM openjdk:17-jdk

WORKDIR /app

COPY /build/libs/*.jar test-sp-customerEntity.jar

EXPOSE 8080

CMD ["java", "-jar", "test-sp-customerEntity.jar"]