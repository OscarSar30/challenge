FROM openjdk:17-jdk

WORKDIR /app

COPY /build/libs/*.jar test-sp-customer.jar

EXPOSE 8080

CMD ["java", "-jar", "test-sp-customer.jar"]