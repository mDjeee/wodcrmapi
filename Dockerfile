FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/wodcrmapi.jar wodcrmapi.jar
EXPOSE 8080
CMD ["java","-jar","wodcrmapi.jar"]