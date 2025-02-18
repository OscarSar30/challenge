FROM openjdk:17-jdk

WORKDIR /app

COPY /build/libs/*.jar test-sp-accounts-movements.jar

EXPOSE 8080

CMD ["java", "-jar", "test-sp-accounts-movements.jar"]